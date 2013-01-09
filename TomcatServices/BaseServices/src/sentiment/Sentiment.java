package sentiment;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import sentiment.classifier.Item;
import sentiment.classifier.WeightedMajority;

@WebService(targetNamespace = "http://aic.service.sentiment/", endpointInterface = "sentiment.ISentiment", portName = "Sentiment", serviceName = "SentimentService")
public final class Sentiment implements ISentiment {
	// Set to true when using mockup sentiment service.
	private static final boolean MOCKUP = false;

	Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	@Override
	public Double compute(String text) {
		if (MOCKUP) {
			logger.debug("Sentiment.compute: text=\"" + text + "\"");
			return new Double(text);
		} else {
			// Final service code.

			Double result = 0.0;

			if (SentimentServiceInitializer.loaded()) {
				logger.debug("Sentiment.compute: " + text);
				WeightedMajority wm = SentimentServiceInitializer
						.getWeightedMajority();

				try {
					Item item = wm.weightedClassify(text);

					logger.debug("Naive Bayes:     " + item.getCl2pol().get(1)); // weight:
																					// wm.get_cl2weight().get(1)
					logger.debug("J48:             " + item.getCl2pol().get(2)); // weight:
																					// wm.get_cl2weight().get(2)
					logger.debug("VotedPerceptron: " + item.getCl2pol().get(3)); // weight:
																					// wm.get_cl2weight().get(3)

					logger.debug("Voted Result: " + item.getPolarity());

					result = wm.averageClassify(text);
					logger.debug("Average Result: " + result);

					// result = Double.valueOf(item.getPolarity()) / 4.0;
				} catch (Exception e) {
					logger.error("Exception in Sentiment.compute: "
							+ e.getMessage());
				}
			} else {
				logger.warn("Sentiment.compute: Init not loaded");
			}
			return result;
		}
	}

	@Override
	public Double computeAverage(String[] texts) {
		if (texts.length == 0)
			return Double.NaN;
		double averageSentiment = 0.0;
		for (String text : texts)
			averageSentiment += compute(text) / texts.length;
		return averageSentiment;
	}

}
