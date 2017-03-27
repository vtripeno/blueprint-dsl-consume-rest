package pacote.interfaces;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.soap.SOAPMessage;

import pacote.contracts.Person;

@WebService (serviceName = "PersonService")
//@SOAPBinding(style = Style.DOCUMENT, use=Use.LITERAL)
public interface IPerson {
	
	SOAPMessage getPerson(@WebParam(name="person")Person person) throws Exception;
}
