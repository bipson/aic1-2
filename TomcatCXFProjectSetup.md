# Introduction #
This is my proposed way of dealing with the Geronimo problem.

Yesterday, I tried 7 hours to get the base services to play nice with WCF without success, so I started looking for alternatives.

In only 2 hours I implemented all mockup services and two Workflows using Eclipse/Axis2 before realizing Axis2 is not Jax-Ws compliant. Dang. But I learned how to integrate everything nice with Eclipse and thought, that has to work with CXF too!

After 3 more hours I can present a really fine approach to get everything automatically in place:
  * Annotations (but you have to re-add the web service to re-create the WSDL)
  * web.xml
  * cxf-servlet.xml

Tomcat is launched and redeployment handled automatically, and the generated WSDL is really, really, really nice. Even Juraj can't find any objections in there ;)

So please try it for yourselves and tell me what you think. Again, no xml-crunching needed, no dependencies apart from Tomcat and CXF you have to download, no WAR file handling.

# Setup #
## Tomcat ##
Download Tomcat at: [apache-tomcat-7.0.33-windows-x86.zip](http://tomcat.apache.org/download-70.cgi#7.0.33)

Extract the zip file to: `C:\tomcat`. Make sure `C:\tomcat\bin` refers to the `bin` folder of Tomcat.

## CXF ##
Download CXF at: [apache-cxf-2.7.0.zip](http://cxf.apache.org/download.html)

Extract the zip file to: `C:\cxf`. Make sure `C:\cxf\bin` refers to the `bin` folder of CXF.

## Eclipse Project Setup ##
Start Eclipse into a new workspace and go to the workbench afterwards.

Select File/New/Other.../Web/Dynamic Web Project. Click Next.

Enter the project name: BaseServices

Click the "New Runtime..." button. Select "Apache Tomcat v7.0" and click Next. Enter `C:\tomcat` into the "Tomcat installation directory". Click the "Installed JREs..." button. If `jre6` is not displayed, click "Add...", choose "Standard VM" and navigate to `C:\Program Files\Java\jre6` using the "Directory..." button. Finally, click "Finish", check jre6, click "OK" and select "jre6" from the combo box. Click "Finish" again.

Click "Next" two times and then check "Generate web.xml deployment descriptor". Click "Finish" one last time. This is going to take a while...

## Integrate CXF ##
Click on Window/Preferences and navigate to Web Services/CXF 2.x Preferences. Click the "Add..." button and enter `C:\cxf` at the "CXF home" textbox. Click "Finish". Check the entry you just added. Select the "Java2WS" tab. Uncheck "Generate Wrapper and Fault Beans" and uncheck "Generate separate XSD for the types". Click the "JAX-WS" tab. Select the following Annotations: @WebMethod, @WebParam, @WebResult. Click "Endpoint Config". Select "Use CXF Servlet" so CXF creates cxf-servlet.xml instead of cxf-bean.xml and uses the CXFContextListener. Click "OK".

## Add Tomcat to known Eclipse Servers ##
Click the "Servers" tab in the bottom pane. Click on the "new server wizard" link. Make sure "Tomcat v7.0 Server" is selected, the hostname is "localhost" and the runtime environment is the one previously created. Click "Next" and click the "Add All" button. Click "Finish".

## Create the first service ##
Add an interface called "IPayment" into the Java Resources/src folder with package name "payment". Define it like this:
```
package payment;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(name = "IPayment", targetNamespace = "http://aic.service.payment/")
public interface IPayment {
    @WebMethod(operationName = "Pay", action = "urn:Pay")
	@WebResult(name = "paymentSucceeded")
	Boolean Pay(@WebParam(name = "ourAccountData") String ourAccountData, @WebParam(name = "userAccountData") String userAccountData, @WebParam(name = "amount") Double amount);
}
```

Add a class called "Payment" into the same package that implements the interface "IPayment". Define it like this:
```
package payment;

import javax.jws.WebService;

@WebService(targetNamespace = "http://aic.service.payment/", endpointInterface = "payment.IPayment", portName = "Payment", serviceName = "PaymentService")
public class Payment implements IPayment {
    @Override
    public Boolean Pay(String ourAccountData, String userAccountData, Double amount) {
            return true;
    }
}
```

Now, right click the "BaseServices" project, choose New/Other... and choose Web Services/Web Service. Click "Next".
Choose "Bottom up Java bean Web Service" and enter `payment.Payment` into the "Service implementation" text box. Click the "Web Service runtime" link and select "Apache CXF 2.x" before clicking "OK". Check "Monitor the Web service" so we can use Eclipse's TCP/IP Monitor to see the incoming and outgoing SOAP messages and click "Next". Check "Use a Service Endpoint Interface". Select "Select an SEI" and choose `payment.IPayment` from the combo box before clicking "Next".

Check if these annotations are set: WebMethod, WebParam, WebResult. Click "Next". Finally click "Next". Lastly, click the button "Start server" to launch Tomcat and deploy the Payment service. The red text that appears are not error messages but output of Tomcat. When the server is startet, click "Finish".

## Fixing cxf-servlet.xml ##
There is one extra step we have to take. Cxf-servlet.xml is created with the default target namespace. If you tried to access the service now, Tomcat would be unable to create an endpoint because it would look for `{http://payment/}Payment` instead of `{http://aic.service.payment/}Payment`. This is annoying but easy to fix. In the BaseServices project, open the WebContent/WEB-INF folder and open cxf-servlet.xml. You might have to click the "Source" tab to see the XML and not the design surface. This file is used by Tomcat to identify the service endpoints. In there, you find a `<jaxws:endpoint>` tag that has the attribute `xmlns:tns="http://payment/"`. Change this attribute to: `xmlns:tns="http://aic.service.payment/"`. Remember that URIs have to match exactly, so the last slash is important. Now open the "Servers" view, click on the server and then the green play button on the right. This restarts Tomcat and reloads all settings including cxf-servlet.xml.

## Checking if the service is deployed ##
Open Firefox and navigate to `http://localhost:8080/BaseServices/services/`. You should see a service overview page containing the Payment Service incl. WSDL link.

Just to show the fine WSDL, i put it down here:

```
<?xml version="1.0" encoding="UTF-8"?>
<wsdl:definitions name="PaymentService" targetNamespace="http://aic.service.payment/" xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/" xmlns:tns="http://aic.service.payment/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/">
  <wsdl:types>
    <xs:schema elementFormDefault="unqualified" targetNamespace="http://aic.service.payment/" version="1.0" xmlns:tns="http://aic.service.payment/" xmlns:xs="http://www.w3.org/2001/XMLSchema">
<xs:element name="Pay" type="tns:Pay"/>
<xs:element name="PayResponse" type="tns:PayResponse"/>
<xs:complexType name="Pay">
    <xs:sequence>
      <xs:element minOccurs="0" name="ourAccountData" type="xs:string"/>
      <xs:element minOccurs="0" name="userAccountData" type="xs:string"/>
      <xs:element minOccurs="0" name="amount" type="xs:double"/>
    </xs:sequence>
  </xs:complexType>
<xs:complexType name="PayResponse">
    <xs:sequence>
      <xs:element minOccurs="0" name="paymentSucceeded" type="xs:boolean"/>
    </xs:sequence>
  </xs:complexType>
</xs:schema>
  </wsdl:types>
  <wsdl:message name="Pay">
    <wsdl:part name="parameters" element="tns:Pay">
    </wsdl:part>
  </wsdl:message>
  <wsdl:message name="PayResponse">
    <wsdl:part name="parameters" element="tns:PayResponse">
    </wsdl:part>
  </wsdl:message>
  <wsdl:portType name="IPayment">
    <wsdl:operation name="Pay">
      <wsdl:input name="Pay" message="tns:Pay">
    </wsdl:input>
      <wsdl:output name="PayResponse" message="tns:PayResponse">
    </wsdl:output>
    </wsdl:operation>
  </wsdl:portType>
  <wsdl:binding name="PaymentServiceSoapBinding" type="tns:IPayment">
    <soap:binding style="document" transport="http://schemas.xmlsoap.org/soap/http"/>
    <wsdl:operation name="Pay">
      <soap:operation soapAction="urn:Pay" style="document"/>
      <wsdl:input name="Pay">
        <soap:body use="literal"/>
      </wsdl:input>
      <wsdl:output name="PayResponse">
        <soap:body use="literal"/>
      </wsdl:output>
    </wsdl:operation>
  </wsdl:binding>
  <wsdl:service name="PaymentService">
    <wsdl:port name="Payment" binding="tns:PaymentServiceSoapBinding">
      <soap:address location="http://localhost:8080/BaseServices/services/PaymentPort"/>
    </wsdl:port>
  </wsdl:service>
</wsdl:definitions>

```

# Problems #
Like with every approach, there are still some problems, though.
  * When you change the annotations, you have to re-add the web service and effectively overwrite it to change the WSDL.
  * This does not change the cxf-servlet.xml though, so if you change the portName or change the targetNamespace, you have to edit this file by hand.
  * How this supports switching between real and mockup services I still have not figured out.

# For the lazy #
I have added Tomcat, CXF and the Eclipse workspace containing the demo service as a download here: [TomcatCXF.rar](http://aic1-2.googlecode.com/files/TomcatCXF.rar). It contains some more info as well.