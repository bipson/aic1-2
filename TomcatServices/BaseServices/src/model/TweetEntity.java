package model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import twitter.Tweet;
import db.interfaces.IEntity;

@Entity
public class TweetEntity implements IEntity<Long> {
	private static final long serialVersionUID = -7413330675922692688L;
	private Long id;
	private Tweet tweet;

	public TweetEntity() {
	}

	public TweetEntity(Tweet tweet) {
		this.tweet = tweet;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tweet_id")
	public Long getId() {
		return id;
	}

	@Embedded
	public Tweet getTweet() {
		return tweet;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTweet(Tweet tweet) {
		this.tweet = tweet;
	}
}
