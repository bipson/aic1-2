package user;

import javax.jws.WebService;

import model.CompanyEntity;
import db.GenericDAO;

@WebService(targetNamespace = "http://aic.service.user/", endpointInterface = "user.IUser", portName = "User", serviceName = "UserService")
public final class User implements IUser {
	// Set to true when using mockup user service.
	private static final boolean MOCKUP = true;

	private static final String PERSISTENCE_UNIT = "aic.sentiment";

	@Override
	public Boolean add(String companyName, String password) {
		if (MOCKUP) {
			System.out.println("User.add: companyName=\"" + companyName
					+ "\" password=\"" + password + "\"");
			return !(companyName.equals("company1")
					|| companyName.equals("company2") || companyName
						.equals("company7"));
		} else {
			GenericDAO.init(PERSISTENCE_UNIT);
			GenericDAO<CompanyEntity, String> companyDAO = new GenericDAO<CompanyEntity, String>(
					CompanyEntity.class);

			try {
				// if company-name already registered
				if (companyDAO.get(companyName) != null) {
					// write to the log
					return false;
				}

				// try to save new entity
				companyDAO
						.persist(new CompanyEntity(companyName, password, 0.0));

				// check if it went through (because we don't get the exception
				// from the DAO)
				if (companyDAO.get(companyName) == null) {
					// write to the log
					return false;
				}
			} catch (Exception ex) {
				return false;
			} finally {
				GenericDAO.shutdown();
			}

			return true;
		}
	}

	@Override
	public Boolean remove(String companyName) {
		if (MOCKUP) {
			System.out.println("User.remove: companyName=\"" + companyName
					+ "\"");
			return companyName.equals("company1")
					|| companyName.equals("company2")
					|| companyName.equals("company6");
		} else {
			GenericDAO.init(PERSISTENCE_UNIT);
			GenericDAO<CompanyEntity, String> companyDAO = new GenericDAO<CompanyEntity, String>(
					CompanyEntity.class);

			try {
				CompanyEntity company = companyDAO.get(companyName);
				if (company == null) {
					return false;
				}
				companyDAO.delete(company);
				if (companyDAO.get(companyName) != null) {
					return false;
				}
				return true;
			} finally {
				GenericDAO.shutdown();
			}
		}
	}

	@Override
	public Boolean exists(String companyName) {
		if (MOCKUP) {
			System.out.println("User.exists: companyName=\"" + companyName
					+ "\"");
			return companyName.equals("company1")
					|| companyName.equals("company2")
					|| companyName.equals("company5")
					|| companyName.equals("company6");
		} else {
			GenericDAO.init(PERSISTENCE_UNIT);
			GenericDAO<CompanyEntity, String> companyDAO = new GenericDAO<CompanyEntity, String>(
					CompanyEntity.class);
			try {
				return companyDAO.get(companyName) != null;
			} finally {
				GenericDAO.shutdown();
			}
		}
	}

	@Override
	public Boolean checkCredentials(String companyName, String password) {
		if (MOCKUP) {
			System.out.println("User.checkCredentials: companyName=\""
					+ companyName + "\" password=\"" + password + "\"");
			return (companyName.equals("company1") && password
					.equals("c1password"))
					|| (companyName.equals("company2") && password
							.equals("c2password"))
					|| (companyName.equals("company3") && password
							.equals("c3password"));
		} else {
			GenericDAO.init(PERSISTENCE_UNIT);
			GenericDAO<CompanyEntity, String> companyDAO = new GenericDAO<CompanyEntity, String>(
					CompanyEntity.class);
			try {
				CompanyEntity company = companyDAO.get(companyName);
				return (company != null && company.getPassword().equals(
						password));
			} finally {
				GenericDAO.shutdown();
			}
		}
	}

}
