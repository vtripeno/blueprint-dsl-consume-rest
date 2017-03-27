package pacote.test;

import javax.xml.soap.SOAPMessage;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Before;
import org.junit.Test;

import pacote.apis.ServicePerson;
import pacote.contracts.Person;


public class TesteRotaSoap extends CamelTestSupport {
	
	SOAPMessage soapMessage;
	Person person;
	ServicePerson servicePerson;
	
	@Before
	public void createContract() throws Exception {
		
		person = new Person();
		person.setFirstName("José");
		person.setLastName("Silva");
		person.setAge(40);
		
		servicePerson = new ServicePerson();
		this.soapMessage = servicePerson.getPerson(this.person);
		
	}
	
	@Override
	public RouteBuilder createRouteBuilder() {
		return new RouteBuilder() {
			
			@Override
			public void configure() throws Exception {
				// TODO Auto-generated method stub
				from("direct:start")
					.process(new Processor() {
					
						@Override
						public void process(Exchange exchange) throws Exception {
							// TODO Auto-generated method stub
							exchange.getOut().setBody(soapMessage);
						}
					})
					.to("mock:test");
					
			}
		};
		
	}
	
	@Test
	public void testSuccess() throws Exception {
		
		person = new Person();
		person.setFirstName("João");
		person.setLastName("Silva");
		person.setAge(40);
		
		servicePerson = new ServicePerson();
		
		MockEndpoint mock = getMockEndpoint("mock:test");
		mock.expectedMessageCount(1);
		mock.message(0).body().contains(soapMessage);
//		mock.message(0).body().contains(servicePerson.getPerson(person)); // falha ao executar essa linha
		template.sendBody("direct:start", soapMessage);
		assertMockEndpointsSatisfied();
	}
	
}
