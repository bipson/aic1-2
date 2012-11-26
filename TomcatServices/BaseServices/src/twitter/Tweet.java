package twitter;

public final class Tweet {
	private long id;
	private String text;
	private String user;
	private long reTweets;
	
	public Tweet(long id, String text, String user, long reTweets) {
		this.id = id;
		this.text = text;
		this.user = user;
		this.reTweets = reTweets;
	}
	
	public long getID () {
		return id;
	}
	
	public String getText () {
		return text;
	}
	
	public String getUser () {
		return user;
	}
	
	public long getReTweets () {
		return reTweets;
	}
	
	public void setID (long id) {
		this.id = id;
	}
	
	public void setText (String text) {
		this.text = text;
	}
	
	public void setUser (String user) {
		this.user = user;
	}
	
	public void setReTweets (long reTweets) {
		this.reTweets = reTweets;
	}
}
