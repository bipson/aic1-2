package sentiment.util;

import java.io.File;

/**
 * Class with utility methods
 * @author AIC
 *
 */
public class Utils {

	// the root for external files
	private static String filesRoot = Utils.class.getResource("weka.classifiers.bayes.NaiveBayes.model").getPath(); //System.getProperty("user.dir")
	//new File(".").getAbsolutePath()
	
	/**
	 * Method to get the root directory of the external files
	 * @return the files' directory location
	 */
	public static String getFilesRoot() {
		return filesRoot;
	}
}
