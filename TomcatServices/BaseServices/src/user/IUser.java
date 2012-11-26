package user;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(name = "IUser", targetNamespace = "http://aic.service.user/")
public interface IUser {
	@WebMethod(operationName = "Add", action = "urn:Add")
	@WebResult(name = "userAdded")
	Boolean add(
			@WebParam(name = "companyName") String companyName,
			@WebParam(name = "password") String password);
	
	@WebMethod(operationName = "Remove", action = "urn:Remove")
	@WebResult(name = "userRemoved")
	Boolean remove(
			@WebParam(name = "companyName") String companyName);
	
	@WebMethod(operationName = "Exists", action = "urn:Exists")
	@WebResult(name = "userExists")
	Boolean exists(
			@WebParam(name = "companyName") String companyName);
	
	@WebMethod(operationName = "CheckCredentials", action = "urn:CheckCredentials")
	@WebResult(name = "credentialsValid")
	Boolean checkCredentials(
			@WebParam(name = "companyName") String companyName,
			@WebParam(name = "password") String password);
}
