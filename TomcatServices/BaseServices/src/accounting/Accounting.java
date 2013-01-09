package accounting;

import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.CompanyEntity;

import org.apache.log4j.Logger;

@WebService(targetNamespace = "http://aic.service.accounting/", endpointInterface = "accounting.IAccounting", portName = "Accounting", serviceName = "AccountingService")
public final class Accounting implements IAccounting {
	// Set to true when using mockup accounting service.
	private static final boolean MOCKUP = false;

	public Logger log = Logger.getLogger(Accounting.class);

	private static final EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("aic.sentiment");

	@Override
	public Double getBill(String companyName) {
		if (MOCKUP) {
			log.debug("[MOCKUP] getBill() - companyName= \"" + companyName
					+ "\"");
			if (companyName.equals("company1")) {
				return 34.5;
			} else if (companyName.equals("company2")
					|| companyName.equals("company5")
					|| companyName.equals("company6")) {
				return 0.0;
			} else if (companyName.equals("company3")) {
				return 1.0;
			} else {
				return Double.NaN;
			}
		} else {

			EntityManager manager = emf.createEntityManager();

			try {
				CompanyEntity company = manager.find(CompanyEntity.class,
						companyName);

				if (company != null) {
					log.debug("getBill() - CompanyName: "
							+ company.getCompanyName() + " CurrentBill: "
							+ company.getCurrentBill());
					return company.getCurrentBill();
				} else {
					log.debug("getBill() - CompanyName: " + company
							+ "- No company found. ");
					return Double.NaN;
				}

			} finally {
				manager.close();
			}
		}

	}

	@Override
	public Boolean charge(String companyName, Double amount) {
		if (MOCKUP) {
			log.debug("[MOCKUP] charge() - companyName=\"" + companyName
					+ "\" amount=\"" + amount + "\"");
			return companyName.equals("company1")
					|| companyName.equals("company2")
					|| companyName.equals("company6");
		} else {

			EntityManager manager = emf.createEntityManager();

			try {

				manager.getTransaction().begin();

				CompanyEntity company = manager.find(CompanyEntity.class,
						companyName);
				if (company == null) {
					manager.getTransaction().rollback();
					return false;
				}

				Double newBill = company.getCurrentBill() + amount;
				if (newBill >= 0) {
					company.setCurrentBill(newBill);
					manager.merge(company);
					manager.getTransaction().commit();

					log.debug(" charge() - CompanyName: "
							+ company.getCompanyName() + " NewBill: " + newBill);

					return true;
				} else {
					log.debug(" charge() - CompanyName: "
							+ company.getCompanyName()
							+ " The bill value can not be under 0. New value would be "
							+ newBill);
					manager.getTransaction().rollback();
					return false;
				}

			} finally {
				manager.close();
			}

		}
	}

	@Override
	public String getOurAccountData() {
		if (MOCKUP) {
			log.debug("[MOCKUP] getOurAccountData() ");
			return "{Our Company Account Information}";
		} else {
			log.debug("getOurAccountData() ");
			return "{Our Company Account Information}";
		}
	}

}
