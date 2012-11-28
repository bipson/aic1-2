package sentiment;

import javax.jws.WebService;

@WebService(targetNamespace = "http://aic.service.sentiment/",
            endpointInterface = "sentiment.ISentiment",
            portName = "Sentiment",
            serviceName = "SentimentService")
public final class Sentiment implements ISentiment {
	//Set to true when using mockup sentiment service.
	private static final boolean MOCKUP = true;
	
	@Override
	public Double compute(String text) {
		if(MOCKUP) {
			System.out.println("Sentiment.compute: text=\"" + text + "\"");
			return new Double(text);
		} else {
			// Final service code.
			return 0.5;
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