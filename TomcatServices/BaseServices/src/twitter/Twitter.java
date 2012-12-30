package twitter;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.TweetEntity;

import org.apache.log4j.Logger;

@WebService(targetNamespace = "http://aic.service.twitter/", endpointInterface = "twitter.ITwitter", portName = "Twitter", serviceName = "TwitterService")
public final class Twitter implements ITwitter {
	// Set to true when using mockup twitter service.
	private static final boolean MOCKUP = false;

	private static Logger logger = Logger.getLogger(Twitter.class
			.getSimpleName());
	private static final EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("aic.sentiment");

	@Override
	@SuppressWarnings("unchecked")
	public Tweet[] fetchTweets(String searchString, Date from, Date to) {
		if (MOCKUP) {
			logger.debug("Twitter.fetchTweets: searchString=\"" + searchString
					+ "\" from=\"" + from + "\" to=\"" + to + "\"");
			if (searchString.equals("company1")) {
				return new Tweet[] {
						new Tweet(1, "0.2", "user", 0, new Date()),
						new Tweet(2, "0.1", "user", 0, new Date()),
						new Tweet(3, "0.2", "user", 0, new Date()),
						new Tweet(4, "0.3", "user", 0, new Date()),
						new Tweet(5, "0.4", "user", 0, new Date()),
						new Tweet(6, "0.4", "user", 0, new Date()),
						new Tweet(7, "0.4", "user", 0, new Date()),
						new Tweet(8, "0.5", "user", 0, new Date()),
						new Tweet(9, "0.7", "user", 0, new Date()),
						new Tweet(10, "0.9", "user", 0, new Date()) };
			} else if (searchString.equals("company2")) {
				return new Tweet[] {
						new Tweet(1, "0.3", "user", 0, new Date()),
						new Tweet(2, "0.5", "user", 0, new Date()),
						new Tweet(3, "0.7", "user", 0, new Date()),
						new Tweet(4, "0.8", "user", 0, new Date()),
						new Tweet(5, "0.9", "user", 0, new Date()),
						new Tweet(6, "1.0", "user", 0, new Date()) };
			} else {
				return new Tweet[0];
			}
		} else {
			EntityManager manager = emf.createEntityManager();
			String queryString = "SELECT DISTINCT tweet FROM TweetEntity tweet "
					+ "INNER JOIN tweet.company c "
					+ "WHERE c.companyName = :company "
					+ "AND :from <= tweet.tweet.created "
					+ "AND tweet.tweet.created < :to ";
			Query query = manager.createQuery(queryString);

			query.setParameter("company", searchString);
			query.setParameter("from", from);
			query.setParameter("to", to);
			List<TweetEntity> result = query.getResultList();
			ArrayList<Tweet> tweets = new ArrayList<Tweet>();
			Iterator<TweetEntity> it = result.iterator();
			while (it.hasNext()) {
				tweets.add(it.next().getTweet());
			}
			return tweets.toArray(new Tweet[0]);
		}
	}

	@Override
	public String[] extractText(Tweet[] tweets) {
		String[] texts = new String[tweets.length];
		for (int index = tweets.length - 1; index >= 0; index--)
			texts[index] = tweets[index].getText();
		return texts;
	}

	@Override
	public Boolean attachListener(String filter) {
		logger.debug("Twitter.attachListener: filter=\"" + filter + "\"");
		if (MOCKUP) {
			return !filter.equals("company3");
		} else {
			TwitterReg.put(filter);
			return true;
		}
	}

	@Override
	public Boolean detachListener(String filter) {
		logger.debug("Twitter.detachListener: filter=\"" + filter + "\"");
		if (MOCKUP) {
			return !filter.equals("company2");
		} else {
			TwitterReg.remove(filter);
			return true;
		}
	}

}
