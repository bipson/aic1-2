package user;

import javax.jws.WebService;

@WebService(targetNamespace = "http://aic.service.user/",
            endpointInterface = "user.IUser",
            portName = "User",
            serviceName = "UserService")
public final class User implements IUser {
	// Set to true when using mockup user service.
	private static final boolean MOCKUP = true;

	@Override
	public Boolean add(String companyName, String password) {
		if(MOCKUP) {
			return companyName != "company1" && companyName != "company2";
		} else {
			// Final service code.
			return true;
		}
	}

	@Override
	public Boolean remove(String companyName) {
		if(MOCKUP) {
			return companyName == "company1" || companyName == "company2";
		} else {
			// Final service code.
			return true;
		}
	}

	@Override
	public Boolean exists(String companyName) {
		if(MOCKUP) {
			return companyName == "company1" || companyName == "company2";
		} else {
			// Final service code.
			return false;
		}
	}

	@Override
	public Boolean checkCredentials(String companyName, String password) {
		if(MOCKUP) {
			return (companyName == "company1" && password == "c1password")
				|| (companyName == "company2" && password == "c2password")
				|| (companyName == "company3" && password == "c3password");
		} else {
			// Final service code.
			return true;
		}
	}

}
