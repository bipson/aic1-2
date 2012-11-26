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
			return true;
		} else {
			// Final service code.
			return true;
		}
	}

	@Override
	public Boolean remove(String companyName) {
		if(MOCKUP) {
			return true;
		} else {
			// Final service code.
			return true;
		}
	}

	@Override
	public Boolean exists(String companyName) {
		if(MOCKUP) {
			return false;
		} else {
			// Final service code.
			return false;
		}
	}

	@Override
	public Boolean checkCredentials(String companyName, String password) {
		if(MOCKUP) {
			return true;
		} else {
			// Final service code.
			return true;
		}
	}

}
