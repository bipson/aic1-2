package at.ac.tuwien.aic.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(targetNamespace = "http://aic.tuwien.ac.at/UserService.wsdl")
public interface IUserService {
	@WebMethod
	public boolean addUser(@WebParam(name = "companyName") String companyName,
			@WebParam(name = "password") String password);

	@WebMethod
	public boolean removeUser(@WebParam(name = "companyName") String companyName);

	@WebMethod
	public boolean userExists(@WebParam(name = "companyName") String companyName);

	@WebMethod
	public boolean checkCredentials(@WebParam(name = "companyName") String companyName,
			@WebParam(name = "password") String password);
}
