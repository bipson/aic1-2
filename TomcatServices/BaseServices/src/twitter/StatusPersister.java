package twitter;

import model.TweetEntity;

import org.apache.log4j.Logger;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import db.GenericDAO;

public class StatusPersister implements StatusListener {
	private static final String PERSISTENCE_UNIT = "aic.sentiment";

	private static Logger logger = Logger.getLogger(StatusPersister.class
			.getSimpleName());

	private GenericDAO<TweetEntity, Long> tweetDAO;

	StatusPersister(String[] search) {
		GenericDAO.init(PERSISTENCE_UNIT);
		tweetDAO = new GenericDAO<TweetEntity, Long>(TweetEntity.class);
	}

	@Override
	public void onException(Exception arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDeletionNotice(StatusDeletionNotice arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScrubGeo(long arg0, long arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStallWarning(StallWarning arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatus(Status status) {
		Tweet tweet = new Tweet(status.getId(), status.getText(), status
				.getUser().getName(), status.getRetweetCount(),
				status.getCreatedAt());

		TweetEntity t = new TweetEntity(tweet);

		logger.debug("Received tweet: " + tweet.getUser() + " - "
				+ tweet.getText());
		tweetDAO.persist(t);

	}

	@Override
	public void onTrackLimitationNotice(int arg0) {
		// TODO Auto-generated method stub
	}

	public void shutdown() {
		GenericDAO.shutdown();
	}
}
