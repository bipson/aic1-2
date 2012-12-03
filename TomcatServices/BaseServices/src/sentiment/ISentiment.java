package sentiment;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(name = "ISentiment", targetNamespace = "http://aic.service.sentiment/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface ISentiment {
	@WebMethod(operationName = "Compute", action = "urn:Compute")
	@WebResult(name = "sentiment")
	Double compute(
		   @WebParam(name = "text") String text);
	
	@WebMethod(operationName = "ComputeAverage", action = "urn:ComputeAverage")
	@WebResult(name = "averageSentiment")
	Double computeAverage(
		   @WebParam(name = "texts") String[] texts);
}
