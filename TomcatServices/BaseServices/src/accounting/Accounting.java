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
			if(companyName == "company1") {
				return 34.5;
			} else if(companyName == "company2") {
				return 0.0;
			} else if(companyName == "company3") {
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
			return companyName == "company1" || companyName == "company2" || companyName == "company3";
		} else {
			// Final service code.
			return true;
		}
	}

	@Override
	public String getOurAccountData() {
		if(MOCKUP) {
			return "{Our Company Account Information}";
		} else {
			// Final service code.
			return "{Our Company Account Information}";
		}
	}

}
