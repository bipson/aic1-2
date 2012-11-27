package payment;

import javax.jws.WebService;

@WebService(targetNamespace = "http://aic.service.payment/",
            endpointInterface = "payment.IPayment", 
            portName = "Payment", 
            serviceName = "PaymentService")
public final class Payment implements IPayment {
	// Set to true when using mockup payment service.
	private static final boolean MOCKUP = true;
	
    @Override
    public Boolean pay(String ourAccountData, String userAccountData, Double amount) {
    	if(MOCKUP) {
    		System.out.println("Payment.pay: ourAccountData=\"" + ourAccountData + "\" userAccountData=\"" + userAccountData + "\" amount=\"" + amount + "\"");
            return userAccountData.equals("{User Account Information}");
    	} else {
    		// Final service code.
    		return true;
    	}
    }
}
