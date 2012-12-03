package sentiment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import sentiment.classifier.ClassifierBuilder;
import sentiment.classifier.IClassifier;
import sentiment.classifier.WeightedMajority;
import sentiment.classifier.WekaClassifier;

public class SentimentServiceInitializer extends HttpServlet {
	private static final long serialVersionUID = 1l;
	
	private static WeightedMajority wm;
	private static boolean loaded;
	
	public void init(ServletConfig config) throws ServletException {
		System.out.println("Sentiment Initialization goes here.");
		
		
		
		List<IClassifier> classifiers = new LinkedList<IClassifier>();
		ClassifierBuilder cb = new ClassifierBuilder();
		
		try {
			System.out.println("Initializing Naive Bayes ...");
			WekaClassifier wc1 = cb.retrieveClassifier("weka.classifiers.bayes.NaiveBayes");
			System.out.println("Initializing J48 ...");
			WekaClassifier wc2 = cb.retrieveClassifier("weka.classifiers.trees.J48");
			System.out.println("Initializing VotedPerceptron ...");
			WekaClassifier wc3 = cb.retrieveClassifier("weka.classifiers.functions.VotedPerceptron");
			classifiers.add(wc1);
			classifiers.add(wc2);
			classifiers.add(wc3);
			
			wm  = new WeightedMajority(classifiers);
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String read;
			System.out.println("finished initializing!");
		} catch(Exception e) {
			System.err.println("Exception in Sentiment: "+e.getMessage());
		}
		
		System.out.println("loaded");
		loaded = true;
	}
	
	
	public static boolean loaded() {
		return loaded;
	}
	
	public static WeightedMajority getMajority() {
		return wm;
	}
	
}
