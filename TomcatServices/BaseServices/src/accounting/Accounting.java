package accounting;

import javax.jws.WebService;

@WebService(targetNamespace = "http://aic.service.accounting/",
            endpointInterface = "accounting.IAccounting",
            portName = "Accounting",
            serviceName = "AccountingService")
public final class Accounting implements IAccounting {
	// Set to true when using mockup accounting service.
	private static final boolean MOCKUP = true;

	@Override
	public Double getBill(String companyName) {
		if(MOCKUP) {
			System.out.println("Accounting.getBill: companyName=\"" + companyName + "\"");
			if(companyName.equals("company1")) {
				return 34.5;
			} else if(companyName.equals("company2") || companyName.equals("company5") || companyName.equals("company6")) {
				return 0.0;
			} else if(companyName.equals("company3")) {
				return 1.0;
			} else {
				return Double.NaN;
			}
		} else {
			// Final service code.
			return 34.5;
		}
	}

	@Override
	public Boolean charge(String companyName, Double amount) {
		if(MOCKUP) {
			System.out.println("Accounting.charge: companyName=\"" + companyName + "\" amount=\"" + amount + "\"");
			return companyName.equals("company1") || companyName.equals("company2") || companyName.equals("company6");
		} else {
			// Final service code.
			return true;
		}
	}

	@Override
	public String getOurAccountData() {
		if(MOCKUP) {
			System.out.println("Accounting.getOurAccountData");
			return "{Our Company Account Information}";
		} else {
			// Final service code.
			return "{Our Company Account Information}";
		}
	}

}
