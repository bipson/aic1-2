package twitter;

import javax.jws.WebService;
import java.util.Date;

@WebService(targetNamespace = "http://aic.service.twitter/",
            endpointInterface = "twitter.ITwitter",
            portName = "Twitter",
            serviceName = "TwitterService")
public final class Twitter implements ITwitter {
	// Set to true when using mockup twitter service.
	private static final boolean MOCKUP = true;

	@Override
	public Tweet[] fetchTweets(String searchString, Date from, Date to) {
		if(MOCKUP) {
			if(searchString == "company1") {
				return new Tweet[] {
					new Tweet(1, "0.1", "user", 0),
					new Tweet(2, "0.1", "user", 0),
					new Tweet(3, "0.2", "user", 0),
					new Tweet(4, "0.3", "user", 0),
					new Tweet(5, "0.4", "user", 0),
					new Tweet(6, "0.4", "user", 0),
					new Tweet(7, "0.4", "user", 0),
					new Tweet(8, "0.5", "user", 0),
					new Tweet(9, "0.7", "user", 0),
					new Tweet(10, "0.9", "user", 0)
				};
			} else if(searchString == "company2") {
				return new Tweet[] {
					new Tweet(1, "0.3", "user", 0),
					new Tweet(2, "0.5", "user", 0),
					new Tweet(3, "0.6", "user", 0),
					new Tweet(4, "0.7", "user", 0),
					new Tweet(5, "0.8", "user", 0),
					new Tweet(6, "1.0", "user", 0)
				};
			} else if(searchString == "company3") {
				return new Tweet[] {
					new Tweet(1, "0.1", "user", 0),
					new Tweet(2, "0.5", "user", 0),
					new Tweet(3, "0.7", "user", 0),
					new Tweet(5, "0.9", "user", 0)
				};
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
		for(int index = tweets.length - 1; index >= 0; index--)
			texts[index] = tweets[index].getText();
		return texts;
	}

	@Override
	public Boolean attachListener(String filter) {
		if(MOCKUP) {
			return true;
		} else {
			// Final service code.
			return true;
		}
	}

	@Override
	public Boolean detachListener(String filter) {
		if(MOCKUP) {
			return true;
		} else {
			// Final service code.
			return true;
		}
	}

}
