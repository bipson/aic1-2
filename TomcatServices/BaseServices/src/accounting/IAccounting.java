package accounting;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(name = "IAccounting", targetNamespace = "http://aic.service.accounting/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface IAccounting {
	@WebMethod(operationName = "GetBill", action = "urn:GetBill")
	@WebResult(name = "currentBill")
	Double getBill(
		   @WebParam(name = "companyName") String companyName);
	
	@WebMethod(operationName = "Charge", action = "urn:Charge")
	@WebResult(name = "chargingSucceeded")
	Boolean charge(
			@WebParam(name = "companyName") String companyName,
			@WebParam(name = "amount") Double amount);
	
	@WebMethod(operationName = "GetOurAccountData", action = "urn:GetOurAccountData")
	@WebResult(name = "ourAccountData")
	String getOurAccountData();
}
