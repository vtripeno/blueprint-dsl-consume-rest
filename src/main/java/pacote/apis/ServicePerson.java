package pacote.apis;

import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import pacote.contracts.Person;
import pacote.interfaces.IPerson;

@WebService(endpointInterface = "pacote.teste.interfaces.IPerson", name = "ServicePerson")
public class ServicePerson implements IPerson {

	@Override
	public SOAPMessage getPerson(Person person) throws Exception {
		// TODO Auto-generated method stub
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();
		
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("per", "http://www.examples.com/wsdl/PersonService.wsdl");
		
		SOAPBody soapBody = envelope.getBody();
		QName bodyPerson = new QName("per:person");
		
		QName firstName = new QName("firstName");
		QName lastName = new QName("lastName");
		QName age = new QName("age");
		
		SOAPBodyElement soapBodyElement = soapBody.addBodyElement(bodyPerson);
		
		SOAPElement soapElement = soapBodyElement.addChildElement(firstName);
		soapElement.addTextNode(person.getFirstName());
		soapElement = soapBodyElement.addChildElement(lastName);
		soapElement.addTextNode(person.getLastName());
		soapElement = soapBodyElement.addChildElement(age);
		soapElement.addTextNode(String.valueOf(person.getAge()));
		
		soapMessage.saveChanges();
		
		return soapMessage;
	}


}
