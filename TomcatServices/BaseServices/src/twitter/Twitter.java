package twitter;

import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;

@WebService(targetNamespace = "http://aic.service.twitter/",
            endpointInterface = "twitter.ITwitter",
            portName = "Twitter",
            serviceName = "TwitterService")
public final class Twitter implements ITwitter {
	// Set to true when using mockup twitter service.
	private static final boolean MOCKUP = true;

	@Override
	public Tweet[] fetchTweets(String searchString, XMLGregorianCalendar from, XMLGregorianCalendar to) {
		if(MOCKUP) {
			return new Tweet[] {
				new Tweet(1, "hay man. What's going on over at AICcompany? they rock! #aic", "philipp", 0),
				new Tweet(2, "lol @ AICcompany. not BAD!", "andy", 20),
				new Tweet(3, "hai guys, this is something unrelated #fluke", "christian", 3),
				new Tweet(4, "AICcompany sucks! they are bad! they are stupid!", "juraj", 18600),
				new Tweet(5, "I must say @AICcompany you have opened my eyes! good work!", "philip", 19),
				new Tweet(6, "anyone knows what AICcompany do? they seem shitty.", "csaba", 6)
			};
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
