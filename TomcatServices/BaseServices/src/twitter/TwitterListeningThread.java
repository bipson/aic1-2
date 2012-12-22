package twitter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.FilterQuery;
import twitter4j.StatusListener;
import twitter4j.StatusStream;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterListeningThread extends Thread {

	private static Logger logger = LoggerFactory
			.getLogger(TwitterListeningThread.class.getSimpleName());

	private String search;
	private StatusStream stream;
	private StatusListener listener;

	TwitterListeningThread(String search) {
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

			long[] followArray = new long[0];
			String[] trackArray = new String[1];
			trackArray[0] = search;

			stream = twitterStream.getFilterStream(new FilterQuery(0,
					followArray, trackArray));
		} catch (TwitterException te) {
			te.printStackTrace();
		}
	}

	public void close() {
		try {
			stream.close();
			((StatusPersister) listener).shutdown();
		} catch (IOException e) {
			logger.info("Error while closing Listener: {}, {}", e.getCause(),
					e.getMessage());
		}
	}

	public void run() {
		logger.debug("new listener {}", search);
		listener = new StatusPersister(search);

		try {
			while (true) {
				stream.next(listener);
			}
		} catch (TwitterException e) {
			logger.debug(
					"TwitterListeningThread caught TwitterException: {}, {}",
					e.getCause(), e.getMessage());
			logger.debug("TwitterListeningThread exciting...");
		} finally {
			this.close();
		}

	}
}
