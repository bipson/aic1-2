package twitter;

import java.util.Date;

import javax.jws.WebService;

@WebService(targetNamespace = "http://aic.service.twitter/", endpointInterface = "twitter.ITwitter", portName = "Twitter", serviceName = "TwitterService")
public final class Twitter implements ITwitter {
	// Set to true when using mockup twitter service.
	private static final boolean MOCKUP = false;

	@Override
	public Tweet[] fetchTweets(String searchString, Date from, Date to) {
		if (MOCKUP) {
			System.out.println("Twitter.fetchTweets: searchString=\""
					+ searchString + "\" from=\"" + from + "\" to=\"" + to
					+ "\"");
			if (searchString.equals("company1")) {
				return new Tweet[] {
						new Tweet(1, "0.1", "user", 0, new Date()),
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
			// Final service code.
			return null;
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
		if (MOCKUP) {
			System.out.println("Twitter.attachListener: filter=\"" + filter
					+ "\"");
			return !filter.equals("company3");
		} else {
			// Final service code.
			return true;
		}
	}

	@Override
	public Boolean detachListener(String filter) {
		if (MOCKUP) {
			System.out.println("Twitter.detachListener: filter=\"" + filter
					+ "\"");
			return !filter.equals("company2");
		} else {
			// Final service code.
			return true;
		}
	}

}
