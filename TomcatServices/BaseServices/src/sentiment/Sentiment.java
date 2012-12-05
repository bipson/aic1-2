package sentiment;

import javax.jws.WebService;

import sentiment.classifier.Item;
import sentiment.classifier.WeightedMajority;

@WebService(targetNamespace = "http://aic.service.sentiment/",
            endpointInterface = "sentiment.ISentiment",
            portName = "Sentiment",
            serviceName = "SentimentService")
public final class Sentiment implements ISentiment {
	//Set to true when using mockup sentiment service.
	private static final boolean MOCKUP = false;
	
	@Override
	public Double compute(String text) {
		if(MOCKUP) {
			System.out.println("Sentiment.compute: text=\"" + text + "\"");
			return new Double(text);
		} else {
			// Final service code.
			
			Double result = 0.0;
			
			if(SentimentServiceInitializer.loaded()) {
				System.out.println("Sentiment.compute: "+text);
				WeightedMajority wm = SentimentServiceInitializer.getWeightedMajority();
				
				
				try {
					Item item = wm.weightedClassify(text);
					
					System.out.println("Naive Bayes:     "+item.getCl2pol().get(1));  // weight: wm.get_cl2weight().get(1)
					System.out.println("J48:             "+item.getCl2pol().get(2));  // weight: wm.get_cl2weight().get(2)
					System.out.println("VotedPerceptron: "+item.getCl2pol().get(3));  // weight: wm.get_cl2weight().get(3)
					
					System.out.println("Voted Result: "+item.getPolarity());
					
					result = wm.averageClassify(text);
					System.out.println("Average Result: "+result);
					
				//	result = Double.valueOf(item.getPolarity()) / 4.0;
				} catch (Exception e) {
					System.err.println("Exception in Sentiment.compute: "+e.getMessage());
				}
			}
			else {
				System.out.println("Sentiment.compute: Init not loaded");
			}
			return result;
		}
	}

	@Override
	public Double computeAverage(String[] texts) {
		if(texts.length == 0) return Double.NaN;
		double averageSentiment = 0.0;
		for(String text : texts)
			averageSentiment += compute(text) / texts.length;
		return averageSentiment;
	}

}
