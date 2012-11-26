package payment;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(name = "IPayment", targetNamespace = "http://aic.service.payment/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface IPayment {
    @WebMethod(operationName = "Pay", action = "urn:Pay")
    @WebResult(name = "paymentSucceeded")
    Boolean pay(
    		@WebParam(name = "ourAccountData") String ourAccountData, 
    		@WebParam(name = "userAccountData") String userAccountData, 
    		@WebParam(name = "amount") Double amount);
}
