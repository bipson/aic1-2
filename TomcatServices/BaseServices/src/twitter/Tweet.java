package twitter;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@Embeddable
@XmlType(name = "Tweet")
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

	@Column
	public long getID() {
		return id;
	}

	@Column
	@XmlElement(name = "Text")
	public String getText() {
		return text;
	}

	@Column
	@XmlElement(name = "User")
	public String getUser() {
		return user;
	}

	@Column
	@XmlElement(name = "ReTweets")
	public long getReTweets() {
		return reTweets;
	}

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	@XmlElement(name = "Created")
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
