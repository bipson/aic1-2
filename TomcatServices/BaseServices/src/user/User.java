package user;

import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.CompanyEntity;

@WebService(targetNamespace = "http://aic.service.user/",
            endpointInterface = "user.IUser",
            portName = "User",
            serviceName = "UserService")
public final class User implements IUser {
	// Set to true when using mockup user service.
	private static final boolean MOCKUP = true;
	
//	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("aic.sentiment");
	private static final EntityManagerFactory emf = null;
	
	@Override
	public Boolean add(String companyName, String password) {
		if(MOCKUP) {
			System.out.println("User.add: companyName=\"" + companyName + "\" password=\"" + password + "\"");
			return !(companyName.equals("company1") || companyName.equals("company2") || companyName.equals("company7"));
		} else {
			EntityManager manager = emf.createEntityManager();
			manager.getTransaction().begin();
			try {
				manager.persist(new CompanyEntity(companyName, password, 0.0));
				manager.getTransaction().commit();
				return true;
			} catch(Exception ex) { //java.sql.SQLIntegrityConstraintViolationException
				return false;
			} finally {
				manager.close();
			}
		}
	}

	@Override
	public Boolean remove(String companyName) {
		if(MOCKUP) {
			System.out.println("User.remove: companyName=\"" + companyName + "\"");
			return companyName.equals("company1") || companyName.equals("company2") || companyName.equals("company6");
		} else {
			EntityManager manager = emf.createEntityManager();
			try {
				CompanyEntity company = manager.find(CompanyEntity.class, companyName);
				if(company == null)
					return false;
				manager.getTransaction().begin();
				manager.remove(company);
				manager.getTransaction().commit();
				return true;
			} finally {
				manager.close();
			}
		}
	}

	@Override
	public Boolean exists(String companyName) {
		if(MOCKUP) {
			System.out.println("User.exists: companyName=\"" + companyName + "\"");
			return companyName.equals("company1") || companyName.equals("company2") || companyName.equals("company5") || companyName.equals("company6");
		} else {
			EntityManager manager = emf.createEntityManager();
			try {
				CompanyEntity company = manager.find(CompanyEntity.class, companyName);
				return company != null;
			} finally {
				manager.close();
			}
		}
	}

	@Override
	public Boolean checkCredentials(String companyName, String password) {
		if(MOCKUP) {
			System.out.println("User.checkCredentials: companyName=\"" + companyName + "\" password=\"" + password + "\"");
			return (companyName.equals("company1") && password.equals("c1password"))
				|| (companyName.equals("company2") && password.equals("c2password"))
				|| (companyName.equals("company3") && password.equals("c3password"));
		} else {
			EntityManager manager = emf.createEntityManager();
			try {
				CompanyEntity company = manager.find(CompanyEntity.class, companyName);
				return company != null && company.getPassword().equals(password);
			} finally {
				manager.close();
			}
		}
	}

}
