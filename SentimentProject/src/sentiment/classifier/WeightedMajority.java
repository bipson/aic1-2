package sentiment.classifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * class representing weighted majority classifier
 */
public class WeightedMajority {
	
	private Map<Integer, IClassifier> _id2cl;
	private Map<Integer,Double> _cl2weight;
	private final double _beta = 0.5;
	

	public Map<Integer, Double> get_cl2weight() {
		return _cl2weight;
	}
	
	/**
	 * @param classifiers a list of classifiers used in weighted majority algorithm
	 * @throws Exception
	 */
	public WeightedMajority(List<IClassifier> classifiers) throws Exception {
		_id2cl = new HashMap<Integer, IClassifier>();
		_cl2weight = new HashMap<Integer, Double>();
		int i = 1;
		for (IClassifier classifier : classifiers) {
			_id2cl.put(i, classifier);
			_cl2weight.put(i, 1.);
			i++;
		}
	}
	
	/**
	 * classifies a tweet 
	 * @param stringa the tweet to classify
	 * @return the item representing the classified tweet
	 * @throws Exception
	 */
	public Item weightedClassify(String stringa) throws Exception {
		Item ist = new Item(stringa);
		Map<Integer,String> cl2pol = new HashMap<Integer, String>();
		int i = 1;
		double pos = 0;
		double neg = 0;
		int numNeg = 0;
		int numPos = 0;
		while(i<=_id2cl.size()) {
			String pol = _id2cl.get(i).classify(stringa);
			if(pol.equals("0")) {
				neg = neg + _cl2weight.get(i);
				cl2pol.put(i, "0");
				numNeg++;
			}
			else {
				pos = pos + _cl2weight.get(i);
				cl2pol.put(i, "4");
				numPos++;
			}
			i++;
		}
		ist.setCl2pol(cl2pol);
		if(pos>neg)
			ist.setPolarity("4");
		else if(pos<neg)
			ist.setPolarity("0");
		else {
			double n = Math.random();
			if(n>=0.5)
				ist.setPolarity("0");
			else
				ist.setPolarity("4");
		}
		if(numPos > numNeg)
			ist.setTarget("4");
		else
			ist.setTarget("0");
		return ist;
	}
	
	
	/**
	 * Classifies a text and returns the average result of the registered classifiers
	 * @param text the text to classify
	 * @return a Double containing the average sentiment result
	 * @throws Exception is thrown if the text cannot be classified
	 */
	public Double averageClassify(String text) throws Exception {
		
		double pos = 0.0;
		for(int i = 1; i<=_id2cl.size(); i++) {
			String pol = _id2cl.get(i).classify(text);
			
			// positive polarity equals 4
			if(pol.equals("4"))
				pos = pos + _cl2weight.get(i);
			//	pos += 1.0;
			
		}
		return new Double(pos / Double.valueOf(_id2cl.size()));
	}
	
	/**
	 * modifies classifiers' weights
	 * @param item the item with setted target polarity
	 */
	public void setTarget(Item item) {
		if(item.getTarget()!=null) {
			int i = 1;
			while(i<=_id2cl.size()) {
				if(!item.getCl2pol().get(i).equals(item.getTarget())) {
					double oldweight = _cl2weight.get(i);
					_cl2weight.put(i, oldweight*_beta);
				}
				i++;
			}
		}
	}
}
