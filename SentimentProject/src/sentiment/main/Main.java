package sentiment.main;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;


import sentiment.classifier.ClassifierBuilder;
import sentiment.classifier.IClassifier;
import sentiment.classifier.Invoker;
import sentiment.classifier.Item;
import sentiment.classifier.WeightedMajority;
import sentiment.classifier.WekaClassifier;
import sentiment.commands.CalculateWmPrecisionCommand;
import sentiment.commands.ConstructCommand;
import sentiment.commands.ConstructWmCommand;
import sentiment.commands.PrepareTrainCommand;
import sentiment.util.Options;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.VotedPerceptron;
import weka.classifiers.trees.J48;
import wlsvm.WLSVM;

public class Main {
	
	public static void main(String[] args) throws Exception {

/*		//costruzione classificatori
		ClassifierBuilder clb = new ClassifierBuilder();
		Options opt = new Options();
		clb.setOpt(opt);
		opt.setSelectedFeaturesByFrequency(true);
		opt.setNumFeatures(150);
		opt.setRemoveEmoticons(false);
		
		System.out.println("prepare Train");
		clb.prepareTrain();
		System.out.println("prepareTest");
		clb.prepareTest();
		System.out.println("Naive Bayes");
		
		// --------- NaiveBayes ---------
		NaiveBayes nb = new NaiveBayes();
		System.out.println("construct classifier NaiveBayes");
		WekaClassifier wc1 = clb.constructClassifier(nb);
		
		// --------- J48 ----------------
		J48 j48 = new J48();
		System.out.println("construct classifier J48");
		WekaClassifier wc2 = clb.constructClassifier(j48);
		
		// --------- VotedPerceptron ----
		VotedPerceptron vp = new VotedPerceptron();
		WekaClassifier wc3 = clb.constructClassifier(vp);
		
		System.out.print("classify tweet: \"i am very sad\" \n");
		String classify1 = wc1.classify(":-(");
		String classify2 = wc2.classify(":-(");
		String classify3 = wc3.classify(":-(");
		System.out.println("polarity NB: " + classify1);
		System.out.println("polarity J48: " + classify2);
		System.out.println("polarity VP: " + classify3);*/
	
		List<IClassifier> classifiers = new LinkedList<IClassifier>();
		ClassifierBuilder cb = new ClassifierBuilder();
		
		System.out.println("Initializing Naive Bayes ...");
		WekaClassifier wc1 = cb.retrieveClassifier("weka.classifiers.bayes.NaiveBayes");
		System.out.println("Initializing J48 ...");
		WekaClassifier wc2 = cb.retrieveClassifier("weka.classifiers.trees.J48");
		System.out.println("Initializing VotedPerceptron ...");
		WekaClassifier wc3 = cb.retrieveClassifier("weka.classifiers.functions.VotedPerceptron");
		classifiers.add(wc1);
		classifiers.add(wc2);
		classifiers.add(wc3);
		
		WeightedMajority wm  = new WeightedMajority(classifiers);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String read;
		
		while(true) {
			System.out.print("Please provide a text to classify (or type \"quit\"): ");
			read = br.readLine();
			if(read.equals("quit"))
				break;
			
			Item item = wm.weightedClassify(read);
			
			System.out.println("Naive Bayes:     "+item.getCl2pol().get(1));  // weight: wm.get_cl2weight().get(1)
			System.out.println("J48:             "+item.getCl2pol().get(2));  // weight: wm.get_cl2weight().get(2)
			System.out.println("VotedPerceptron: "+item.getCl2pol().get(3));  // weight: wm.get_cl2weight().get(3)
			
			System.out.println("Voted Result: "+item.getPolarity());
			
			Double result = wm.averageClassify(read);
			System.out.println("Average Result: "+result);
		
		}
		
		
	}
}
