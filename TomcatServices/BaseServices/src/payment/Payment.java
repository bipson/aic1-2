package payment;

import javax.jws.WebService;

import org.apache.log4j.Logger;

@WebService(targetNamespace = "http://aic.service.payment/", endpointInterface = "payment.IPayment", portName = "Payment", serviceName = "PaymentService")
public final class Payment implements IPayment {
	// Set to true when using mockup payment service.
	private static final boolean MOCKUP = false;
	private static Logger logger = Logger.getLogger(Payment.class
			.getSimpleName());

	@Override
	public Boolean pay(String ourAccountData, String userAccountData,
			Double amount) {
		if (MOCKUP) {
			logger.debug("Payment.pay: ourAccountData=\"" + ourAccountData
					+ "\" userAccountData=\"" + userAccountData
					+ "\" amount=\"" + amount + "\"");
			return userAccountData.equals("{User Account Information}");
		} else {
			// Final service code.
			return true;
		}
	}
}
