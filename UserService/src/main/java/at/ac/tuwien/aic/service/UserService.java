package at.ac.tuwien.aic.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC)
public class UserService implements IUserService {

	@Override
	@WebMethod
	public boolean addUser(@WebParam(name = "companyName") String companyName,
			@WebParam(name = "password") String password) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	@WebMethod
	public boolean removeUser(@WebParam(name = "companyName") String companyName) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	@WebMethod
	public boolean userExists(@WebParam(name = "companyName") String companyName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@WebMethod
	public boolean checkCredentials(@WebParam(name = "companyName") String companyName,
			@WebParam(name = "password") String password) {
		// TODO Auto-generated method stub
		return true;
	}
}
