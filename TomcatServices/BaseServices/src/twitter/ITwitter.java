package twitter;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.datatype.XMLGregorianCalendar;

@WebService(name = "ITwitter", targetNamespace = "http://aic.service.twitter/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface ITwitter {
	@WebMethod(operationName = "FetchTweets", action = "urn:FetchTweets")
	@WebResult(name = "tweets")
	Tweet[] fetchTweets(
			@WebParam(name = "searchString") String searchString,
			@WebParam(name = "from") XMLGregorianCalendar from,
			@WebParam(name = "to") XMLGregorianCalendar to);
	
	@WebMethod(operationName = "ExtractText", action = "urn:ExtractText")
	@WebResult(name = "texts")
	String[] extractText(
			 @WebParam(name = "tweets") Tweet[] tweets);
	
	@WebMethod(operationName = "AttachListener", action = "urn:AttachListener")
	@WebResult(name = "listenerAttached")
	Boolean attachListener(
			@WebParam(name = "filter") String filter);
	
	@WebMethod(operationName = "DetachListener", action = "urn:DetachListener")
	@WebResult(name = "listenerDetached")
	Boolean detachListener(
			@WebParam(name = "filter") String filter);
}
