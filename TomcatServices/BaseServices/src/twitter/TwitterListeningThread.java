package twitter;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.IOException;

public class TwitterListeningThread extends Thread {
	
	private String search;
	private StatusStream stream;
	
	TwitterListeningThread(String search) {
		this.search = search;
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("cAbjgmy3Lc9t1GCCrvTdg")
                .setOAuthConsumerSecret("gKm9fl7l4sBu7ZMlG9XnqbJO3mcDpah0fm7CMQBkk")
                .setOAuthAccessToken("901496460-iR19N4c3t0uibUfBp0iRPrUT2m6LbdkYgWu290jb")
                .setOAuthAccessTokenSecret("UyEMSFnagxGZFqgijL6um4hGB2PLzBIQFvbbLvHVMMg");

        try {
		    TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
		   
		    long[] followArray = new long[0];
		    String[] trackArray = new String[1];
		    trackArray[0] = search;
		
		    
        
        	stream = twitterStream.getFilterStream(new FilterQuery(0, followArray, trackArray));     
        } catch (TwitterException te){
            te.printStackTrace();
        }
	}
	
	public void close() {
		try {
			stream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run() {
		System.out.println("new listener " + search);
		StatusListener listener = new StatusPersister(search);
		
		try {
			while (true) {
				stream.next(listener);
			}
		} catch (TwitterException e) {
			// done
		}
		
	}
}
