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
			System.out.println("User.add: companyName=\"" + companyName + "\" password=\"" + password + "\"");
			return !(companyName.equals("company1") || companyName.equals("company2"));
		} else {
			// Final service code.
			return true;
		}
	}

	@Override
	public Boolean remove(String companyName) {
		if(MOCKUP) {
			System.out.println("User.remove: companyName=\"" + companyName + "\"");
			return companyName.equals("company1") || companyName.equals("company2");
		} else {
			// Final service code.
			return true;
		}
	}

	@Override
	public Boolean exists(String companyName) {
		if(MOCKUP) {
			System.out.println("User.exists: companyName=\"" + companyName + "\"");
			return companyName.equals("company1") || companyName.equals("company2");
		} else {
			// Final service code.
			return false;
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
			// Final service code.
			return true;
		}
	}

}
