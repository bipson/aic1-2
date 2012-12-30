package twitter;

import java.util.HashMap;

import org.apache.log4j.Logger;

public class TwitterReg {
	private static HashMap<String, TwitterListeningThread> registry = null;
	private static Logger logger = Logger.getLogger(TwitterReg.class
			.getSimpleName());

	private static void init() {
		if (registry == null) {
			registry = new HashMap<String, TwitterListeningThread>();
		}
	}

	public static void put(String s) {
		try {
			init();
			TwitterListeningThread n = new TwitterListeningThread(s);
			registry.put(s, n);
			n.start();
			logger.debug("Listener " + s + " started.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void remove(String s) {
		init();
		if (registry.containsKey(s)) {
			TwitterListeningThread n = registry.get(s);
			n.close();
			registry.remove(s);
			logger.debug("Listener " + s + " removed.");
		}

	}

}
