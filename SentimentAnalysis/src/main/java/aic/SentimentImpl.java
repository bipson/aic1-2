package aic;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.jws.WebService;
import datatypes.sentiment.aic.ArrayOfStrings;

import service.sentiment.aic.Sentiment;
import service.sentiment.aic.InitFault;
import service.sentiment.aic.ComputeFault;
import service.sentiment.aic.ComputeAverageFault;



@WebService(		  serviceName = "Sentiment",
                      portName = "SentimentSOAP",
                      targetNamespace = "http://aic.sentiment.service/",
                      wsdlLocation = "WEB-INF/wsdl/sentiment.service.wsdl",
                      endpointInterface = "service.sentiment.aic.Sentiment")                   
public class SentimentImpl implements Sentiment {

	private boolean init;
	
	public void init() throws InitFault {
	/*	List<IClassifier> classifiers = new LinkedList<IClassifier>();
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
			
		} catch(ClassNotFoundException cnfex) {
			System.err.println("ClassNotFoundException in init: "+cnfex.getMessage());
			throw new InitFault("class not found");
		} catch (FileNotFoundException fnfex) {
			System.err.println("FileNotFoundException in init: "+fnfex.getMessage());
			throw new InitFault("file not found");
		} catch (IOException ioex) {
			System.err.println("IOException in init: "+ioex.getMessage());
			throw new InitFault("IOException occurred");
		}
		
		
		try {
			System.out.println("weighted majority");
			wm  = new WeightedMajority(classifiers);
		} catch (Exception e) {
			throw new InitFault("WeightedMajority Exception");
		}
		*/

		System.out.println("Initialization finished");
		init = true;
	}

	
	public double compute(String text) throws ComputeFault {
	
		if(!init) {
			try {
				init();
			} catch( InitFault ife ) {
				System.err.println("InitFaultException in compute: "+ife.getMessage());
				return Double.NaN;
			}
		}
/*
		if( text == null  ||  text.length() == 0 )
			return Double.NaN;
			
		
		Item item = null;
		try {
			// A weighted classification of the text considering all given classifiers
			item = wm.weightedClassify(text);
		} catch (Exception e) {
			throw new ComputeFault("Error in weightedClassify");
		}
	
		// the most common calculated polarity is set as target polarity
		item.setTarget(item.getPolarity());
		
		// the classifiers are assigned a new weight depending on the 
		// correctness of their classification
		wm.setTarget(item);
		
		// Print the individual classifications (for testing)
		System.out.println("Naive Bayes:     "+item.getCl2pol().get(1));
		System.out.println("J48:             "+item.getCl2pol().get(2));
		System.out.println("VotedPerceptron: "+item.getCl2pol().get(3));
		
		// scaling the polarity to the range {0,1}
		double result = Double.valueOf(item.getPolarity()) / 4.0;
		*/
		double result = 1.0;
		
		// return the scaled polarity)
		return result;
	}
	
	
	public double computeAverage(ArrayOfStrings texts) throws ComputeAverageFault {
		
		if(!init) {
			try {
				init();
			} catch( InitFault ife ) {
				System.err.println("InitFaultException in compute: "+ife.getMessage());
				return Double.NaN;
			}
		}
		
	//	texts.getString().
/*			
		if( texts == null  ||  texts.size() = 0 )
			return Double.NaN;
			
		Double result = 0.0;
			
		for( String text : texts ) {
			try {
				result += compute( text );
			} catch (SingleFault sfe) {
				System.err.println("Fault occured on computeAverage");
				return Double.NaN;
			}
		}
		
		result /= texts.size();
		return result;
		*/
		//dummy
		return 0.6;
	}
	
	
	/*
    public boolean removeUser(String parameters) throws RemoveUserFault    { 
        
        System.out.println(parameters);

        try {


            boolean response = true;
            return response;


        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }

    }


    public boolean addUser(AddUserToDatabaseType parameters) throws AddUserFault    { 
        
        System.out.println(parameters);

        try {


            boolean response = true;
            return response;


        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    
    }
*/

}
