package at.tuwien.aic2012.sentiment;

import javax.jws.WebService;
import at.tuwien.aic2012.sentiment.datatypes.ArrayOfStrings;

import at.tuwien.aic2012.sentiment.service.Sentiment;
import at.tuwien.aic2012.sentiment.service.InitFault;


@WebService(		  serviceName = "Sentiment",
                      portName = "at.tuwien.aic2012.sentiment.ServiceSOAP",
                      targetNamespace = "http://at.tuwien.aic2012.sentiment.service/",
                      wsdlLocation = "WEB-INF/wsdl/at/tuwien/aic2012/at.tuwien.aic2012.sentiment.service.wsdl",
                      endpointInterface = "at.tuwien.aic2012.sentiment.service.Sentiment")                   
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
