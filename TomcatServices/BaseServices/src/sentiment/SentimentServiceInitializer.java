package sentiment;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import sentiment.classifier.ClassifierBuilder;
import sentiment.classifier.IClassifier;
import sentiment.classifier.WeightedMajority;
import sentiment.classifier.WekaClassifier;

public class SentimentServiceInitializer extends HttpServlet {
	private static final long serialVersionUID = 1l;

	private static WeightedMajority wm;
	private static boolean loaded;

	public void init(ServletConfig config) throws ServletException {

		Logger logger = Logger.getLogger(this.getClass().getSimpleName());

		logger.debug("Sentiment Service Initialization");

		List<IClassifier> classifiers = new LinkedList<IClassifier>();
		ClassifierBuilder cb = new ClassifierBuilder();

		try {
			logger.debug("Initializing Naive Bayes ...");
			WekaClassifier wc1 = cb
					.retrieveClassifier("weka.classifiers.bayes.NaiveBayes");
			logger.debug("Initializing J48 ...");
			WekaClassifier wc2 = cb
					.retrieveClassifier("weka.classifiers.trees.J48");
			logger.debug("Initializing VotedPerceptron ...");
			WekaClassifier wc3 = cb
					.retrieveClassifier("weka.classifiers.functions.VotedPerceptron");
			classifiers.add(wc1);
			classifiers.add(wc2);
			classifiers.add(wc3);
			wm = new WeightedMajority(classifiers);

			logger.debug("finished initializing!");
		} catch (Exception e) {
			logger.error("Exception in Sentiment: " + e.getMessage());
			e.printStackTrace();
		}

		logger.debug("Sentiment loaded");
		loaded = true;
	}

	public static boolean loaded() {
		return loaded;
	}

	public static WeightedMajority getWeightedMajority() {
		return wm;
	}

}
