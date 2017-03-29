package pacote.mock;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.soap.SOAPMessage;

@WebService (serviceName = "PersonService")
public interface IResponsePerson {
	
	SOAPMessage getResponsePerson(@WebParam(name="soapMessage")SOAPMessage soapMessage) throws Exception;
	
}
