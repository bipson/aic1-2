package twitter;

import org.apache.log4j.Logger;

import twitter4j.FilterQuery;
import twitter4j.StatusListener;
import twitter4j.StatusStream;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterListeningThread extends Thread {

	private static Logger logger = Logger
			.getLogger(TwitterListeningThread.class.getSimpleName());

	private String[] search;
	private StatusStream stream;
	private StatusListener listener;

	TwitterListeningThread(String[] search) {
		this.search = search;
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
				.setOAuthConsumerKey("cAbjgmy3Lc9t1GCCrvTdg")
				.setOAuthConsumerSecret(
						"gKm9fl7l4sBu7ZMlG9XnqbJO3mcDpah0fm7CMQBkk")
				.setOAuthAccessToken(
						"901496460-iR19N4c3t0uibUfBp0iRPrUT2m6LbdkYgWu290jb")
				.setOAuthAccessTokenSecret(
						"UyEMSFnagxGZFqgijL6um4hGB2PLzBIQFvbbLvHVMMg");

		try {
			TwitterStream twitterStream = new TwitterStreamFactory(cb.build())
					.getInstance();
			logger.debug("New TwitterStream configured and created");

			long[] followArray = new long[0];

			stream = twitterStream.getFilterStream(new FilterQuery(0,
					followArray, search));

			logger.debug("New filterStream for " + search.length
					+ " word(s) created");
		} catch (TwitterException te) {
			logger.error("Error while trying to create Stream");
			te.printStackTrace();
		}
	}

	public void close() {
		try {
			stream.close();
			((StatusPersister) listener).shutdown();
		} catch (Exception e) {
			logger.error("Error while closing Listerner: " + e.getCause()
					+ " , " + e.getMessage());
		} finally {
			logger.debug("Listener for " + search.length + " word(s) closed");
		}
	}

	public void run() {
		logger.debug("New Listener for " + search.length + " word(s) started");
		listener = new StatusPersister(search);

		try {
			while (true) {
				stream.next(listener);
			}
		} catch (TwitterException e) {
			logger.error("Listener-Thread caught TwitterException: "
					+ e.getCause() + ", " + e.getMessage());
		} catch (Exception ex) {
			logger.error("Listener-Thread caught Exception: " + ex.getCause()
					+ ", " + ex.getMessage());
		} finally {
			logger.debug("Listener exciting...");
			this.close();
		}

	}
}
