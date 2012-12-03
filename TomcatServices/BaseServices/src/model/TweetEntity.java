package model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import twitter.Tweet;

@Entity
public class TweetEntity {
	private Long id;
	private CompanyEntity company;
    private Tweet tweet;

    public TweetEntity() {
    }

    public TweetEntity(Long id, CompanyEntity company, Tweet tweet) {
            this.id = id;
            this.company = company;
            this.tweet = tweet;
    }
	
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy="increment")
	public Long getId() {
		return id;
	}
	
	@ManyToOne(optional=false)
	@JoinColumn
	public CompanyEntity getCompany() {
		return company;
	}
	
	@Embedded
	public Tweet getTweet() {
		return tweet;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setCompany(CompanyEntity company) {
		this.company = company;
	}
	
	public void setTweet(Tweet tweet) {
		this.tweet = tweet;
	}
}
