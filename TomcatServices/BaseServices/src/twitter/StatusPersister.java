package twitter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.CompanyEntity;
import model.TweetEntity;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

public class StatusPersister implements StatusListener {
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("aic.sentiment");
	@Override
	public void onException(Exception arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeletionNotice(StatusDeletionNotice arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScrubGeo(long arg0, long arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStallWarning(StallWarning arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatus(Status status) {
		Tweet tweet = new Tweet(
				status.getId(),
				status.getText(), 
				status.getUser().getName(), 
				status.getRetweetCount(), 
				status.getCreatedAt()
				); 
		TweetEntity t = new TweetEntity(company, tweet);
		//t.setId(status.getId()); // WHY!
		System.out.println(tweet.getUser() + " - " + tweet.getText());
		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();
	}

	@Override
	public void onTrackLimitationNotice(int arg0) {
		// TODO Auto-generated method stub	
	}
	
	
	private EntityManager manager;
	private CompanyEntity company;
	
	StatusPersister(String search) {		
		this.manager = 	emf.createEntityManager();
		this.company = manager.find(CompanyEntity.class, search);
	}
}
