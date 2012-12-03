package sentiment.classifier;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;


import sentiment.documents.DocumentsSet;
import sentiment.util.ArffFileCreator;
import sentiment.util.Options;
import sentiment.util.Utils;
import weka.classifiers.Classifier;
import weka.classifiers.functions.MultilayerPerceptron;


/**
 * The receiver class
 */
public class ClassifierBuilder {
	
	DocumentsSet _ds;
	private Options opt;
	
	public ClassifierBuilder() {
		_ds = new DocumentsSet();
	}
	
	/**
	 * gets the options of sentiment.classifier builder
	 * @return the options of sentiment.classifier builder
	 */
	public Options getOpt() {
		return opt;
	}
	
	/**
	 * sets given options of sentiment.classifier builder
	 * @param opt options of sentiment.classifier builder
	 */
	public void setOpt(Options opt) {
		this.opt = opt;
	}
	
	/**
	 * prepares data structures for sentiment.classifier train
	 * @throws IOException
	 */
	public void prepareTrain() throws IOException {
		_ds.createFilePreprocessed(Utils.getFilesRoot() + "train.txt", Utils.getFilesRoot() + "train_doc.txt", opt);
		_ds.createIndexTrain(Utils.getFilesRoot() + "train_doc.txt");
		if(this.opt.isSelectedFeaturesByFrequency())
			_ds.getFeat().selectFeaturesByFrequency(2);
		ArffFileCreator fc = new ArffFileCreator();
		fc.setDs(_ds);
		fc.createArff_train(Utils.getFilesRoot() + "train1.arff");
	}
	
	/**
	 * prepares data structures for sentiment.classifier test
	 * @throws IOException
	 */
	public void prepareTest() throws IOException {
		_ds.createFilePreprocessed(Utils.getFilesRoot() + "test_base.txt", Utils.getFilesRoot() + "test_doc.txt", opt);
		_ds.createIndexTest(Utils.getFilesRoot() + "test_doc.txt");
		ArffFileCreator fc = new ArffFileCreator();
		fc.setDs(_ds);
		fc.createArff_test(Utils.getFilesRoot() + "test1.arff");
		
	}
	
	/**
	 * constructs and serializes a Weka sentiment.classifier
	 * @param sentiment.classifier the sentiment.classifier to construct
	 * @return the constructed Weka sentiment.classifier
	 * @throws Exception
	 */
	public WekaClassifier constructClassifier(Classifier classifier) throws Exception {
		WekaClassifier clas = new WekaClassifier();
		clas.setClassifier(classifier);
		if(this.opt.getNumFeatures()!=0)
			clas.selectFeatures(opt.getNumFeatures());
		System.out.println("initialize train");
		clas.train();
		System.out.println("training finished");
		ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(Utils.getFilesRoot() + classifier.getClass().getName() + ".model"));
		System.out.println("serializing sentiment.classifier ...");
		os.writeObject(clas);
		System.out.println("done");
		os.close();
		this.opt.setConstructedClassifier(clas);
		return clas;
	}
	
	/**
	 * constructs and serializes a sentiment.classifier whose name is in options
	 * @throws Exception
	 */
	public void constructClassifierByName() throws Exception {
		if(this.opt.getClassifierName().equals("weka.classifiers.functions.MultilayerPerceptron")) {
			MultilayerPerceptron mp = (MultilayerPerceptron)Class.forName(this.opt.getClassifierName()).newInstance();
			mp.setHiddenLayers("o");
			mp.setTrainingTime(10);
			this.constructClassifier(mp);
		} else {
			System.out.println("Classifier, new Instance: "+opt.getClassifierName());
			Classifier cl = (Classifier)Class.forName(this.opt.getClassifierName()).newInstance();
			System.out.println("construct sentiment.classifier ...");
			this.constructClassifier(cl);
		}
	}
	
	/**
	 * deserializes a sentiment.classifier whose name is given
	 * @param classifierName the sentiment.classifier's name
	 * @return the constructed sentiment.classifier
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public WekaClassifier retrieveClassifier(String classifierName) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(getClass().getResourceAsStream(Utils.getFilesRoot() + classifierName + ".model"));
	//	ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Utils.getFilesRoot() + classifierName + ".model"));
		WekaClassifier wc = (WekaClassifier)ois.readObject();
		ois.close();
		return wc;
	}
	
	/**
	 * constructs a weighted majority sentiment.classifier
	 * @throws Exception
	 */
	public void constructWm() throws Exception {
		List<IClassifier> wc = new LinkedList<IClassifier>();
		for (String str : this.opt.getWmClassifiersName()) {
			wc.add(this.retrieveClassifier(str));
		}
		WeightedMajority wm = new WeightedMajority(wc);
		while(true) {
        	InputStreamReader reader = new InputStreamReader (System.in);
        	BufferedReader myInput = new BufferedReader (reader);
        	String str = new String();
//			System.out.println("Inserisci una stringa da classificare: ");
			System.out.println("Enter a string to be classified: ");
			str = myInput.readLine();
			Item ist = wm.weightedClassify(str);
			System.out.println("Classification: " + ist.getPolarity());
			System.out.println("Enter the correct polarity: ");
			str = myInput.readLine();
			ist.setTarget(str);
		}
	}
	
	/**
	 * calculates weighted majority sentiment.classifier's precision
	 * @throws Exception
	 */
	public void calculateWmPrecision() throws Exception {
		List<IClassifier> wc = new LinkedList<IClassifier>();
		for (String str : this.opt.getWmClassifiersName()) {
			wc.add(this.retrieveClassifier(str));
		}
		WeightedMajority wm = new WeightedMajority(wc);
		int i = 1;
		float correct = 0;
		float[] fun;
		fun = new float[183];
		Preprocesser pr = new Preprocesser();
		Item temp;
        FileInputStream fstream = new FileInputStream(Utils.getFilesRoot() + "test_base.txt");
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        String str, pol;
        while ((strLine = br.readLine()) != null) {
    		String[] items = strLine.split(";;");
    		str = items[5].toLowerCase();
    		pol = items[0];
    		temp = wm.weightedClassify(pr.preprocessDocument(str));
    		temp.setTarget(pol);
    		wm.setTarget(temp);
    		if(temp.getPolarity().equals(temp.getTarget()))
    			correct++;
    		System.out.println(correct/i);
    		System.out.print(wm.get_cl2weight().get(1) + " ");
    		System.out.print(wm.get_cl2weight().get(2) + " ");
    		System.out.println(wm.get_cl2weight().get(3));
    		fun[i-1] = correct/i;
    		i++;
        }
        br.close();
	}
}
