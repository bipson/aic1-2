package user;

import javax.jws.WebService;

import model.CompanyEntity;

import org.apache.log4j.Logger;

import db.GenericDAO;

@WebService(targetNamespace = "http://aic.service.user/", endpointInterface = "user.IUser", portName = "User", serviceName = "UserService")
public final class User implements IUser {
	// Set to true when using mockup user service.
	private static final boolean MOCKUP = false;

	private static final String PERSISTENCE_UNIT = "aic.sentiment";

	private static Logger logger = Logger.getLogger(User.class.getSimpleName());

	@Override
	public Boolean add(String companyName, String password) {
		logger.debug("User.add: companyName=\"" + companyName
				+ "\" password=\"" + password + "\"");
		if (MOCKUP) {
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
					logger.error("Company already exists?");
					return false;
				}

				// try to save new entity
				companyDAO
						.persist(new CompanyEntity(companyName, password, 0.0));

				// check if it went through (because we don't get the exception
				// from the DAO)
				if (companyDAO.get(companyName) == null) {
					logger.error("Company was not persisted?");
					return false;
				}
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				ex.printStackTrace();
				return false;
			} finally {
				GenericDAO.shutdown();
			}

			logger.debug("User added: companyName=\"" + companyName + "\"");
			return true;
		}
	}

	@Override
	public Boolean remove(String companyName) {
		logger.debug("User.remove: companyName=\"" + companyName + "\"");
		if (MOCKUP) {
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
					logger.error("Company was not deleted");
					return false;
				}
				logger.debug("User removed: companyName=\"" + companyName
						+ "\"");
				return true;
			} finally {
				GenericDAO.shutdown();
			}
		}
	}

	@Override
	public Boolean exists(String companyName) {
		logger.debug("User.exists: companyName=\"" + companyName + "\"");

		if (MOCKUP) {
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
			} catch (Exception ex) {
				logger.error("Exception while checking for existing company: "
						+ ex.getMessage() + ", " + ex.getCause());
				return false;
			} finally {
				GenericDAO.shutdown();
			}
		}
	}

	@Override
	public Boolean checkCredentials(String companyName, String password) {
		logger.debug("User.checkCredentials: companyName=\"" + companyName
				+ "\" password=\"" + password + "\"");
		if (MOCKUP) {
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
			} catch (Exception ex) {
				logger.error("Exception while checking for company credentials: "
						+ ex.getMessage() + ", " + ex.getCause());
				return false;
			} finally {
				GenericDAO.shutdown();
			}
		}
	}

}
