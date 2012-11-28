package twitter;

import java.util.Date;

public final class Tweet {
	private long id;
	private String text;
	private String user;
	private long reTweets;
	private Date created;

	public Tweet() {
		this(0, "", "", 0, new Date());
	}

	public Tweet(long id, String text, String user, long reTweets, Date created) {
		this.id = id;
		this.text = text;
		this.user = user;
		this.reTweets = reTweets;
		this.created = created;
	}

	public long getID() {
		return id;
	}

	public String getText() {
		return text;
	}

	public String getUser() {
		return user;
	}

	public long getReTweets() {
		return reTweets;
	}

	public Date getCreated() {
		return created;
	}

	public void setID(long id) {
		this.id = id;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setReTweets(long reTweets) {
		this.reTweets = reTweets;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
}
