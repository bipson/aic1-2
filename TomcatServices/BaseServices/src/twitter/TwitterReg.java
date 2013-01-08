package twitter;

import java.util.HashSet;

import org.apache.log4j.Logger;

public class TwitterReg {
	private static HashSet<String> registry = null;
	private static Logger logger = Logger.getLogger(TwitterReg.class
			.getSimpleName());
	private static TwitterListeningThread curListener = null;

	private static void init() {
		if (registry == null) {
			registry = new HashSet<String>();
		}
	}

	public static void put(String s) {
		try {
			init();

			registry.add(s);
			restartListener();
			logger.debug("Listener " + s + " started.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void restartListener() {
		TwitterListeningThread oldListener = curListener;

		if (registry.size() > 0) {
			curListener = new TwitterListeningThread(
					registry.toArray(new String[0]));
			curListener.start();
		}
		if (oldListener != null)
			oldListener.close();
	}

	public static void remove(String s) {
		init();
		if (registry.contains(s)) {
			registry.remove(s);
			restartListener();
			logger.debug("Listener " + s + " removed.");
		}

	}

}
