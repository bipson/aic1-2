package aic;

import javax.jws.WebService;
import datatypes.sentiment.aic.ArrayOfStrings;

import service.sentiment.aic.Sentiment;
import service.sentiment.aic.InitFault;


@WebService(		  serviceName = "Sentiment",
                      portName = "SentimentSOAP",
                      targetNamespace = "http://aic.sentiment.service/",
                      wsdlLocation = "WEB-INF/wsdl/sentiment.service.wsdl",
                      endpointInterface = "service.sentiment.aic.Sentiment")                   
public class SentimentImpl implements Sentiment {

	private boolean init;
	
	public void init() throws InitFault {

		System.out.println("Initialization finished");
		init = true;
	}

	
	public double compute(String text) {
	
		if(!init) {
			try {
				init();
			} catch( InitFault ife ) {
				System.err.println("InitFaultException in compute: "+ife.getMessage());
				return Double.NaN;
			}
		}

		double result = 1.0;
		
		// return the scaled polarity)
		return result;
	}
	
	
	public double computeAverage(ArrayOfStrings texts) {
		
		if(!init) {
			try {
				init();
			} catch( InitFault ife ) {
				System.err.println("InitFaultException in compute: "+ife.getMessage());
				return Double.NaN;
			}
		}

		//dummy
		return 0.6;
	}

}
