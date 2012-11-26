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
			return 34.5;
		} else {
			// Final service code.
			return 34.5;
		}
	}

	@Override
	public Boolean charge(String companyName, Double amount) {
		if(MOCKUP) {
			return true;
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
