package payment;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(name = "IPayment", targetNamespace = "http://aic.service.payment/")
public interface IPayment {
    @WebMethod(operationName = "Pay", action = "urn:Pay")
    @WebResult(name = "paymentSucceeded")
    Boolean pay(
    		@WebParam(name = "ourAccountData") String ourAccountData, 
    		@WebParam(name = "userAccountData") String userAccountData, 
    		@WebParam(name = "amount") Double amount);
}
