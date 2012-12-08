package twitter;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
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

	@Column(name = "external_id")
	public long getID() {
		return id;
	}

	@Column
	public String getText() {
		return text;
	}

	@Column
	public String getUser() {
		return user;
	}

	@Column
	public long getReTweets() {
		return reTweets;
	}

	@Column
	@Temporal(TemporalType.TIMESTAMP)
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
