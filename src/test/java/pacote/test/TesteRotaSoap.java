package pacote.test;

import javax.xml.soap.SOAPMessage;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
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
		person.setFirstName("TESTE");
		person.setLastName("TESTANDO");
		person.setAge(4000);

		servicePerson = new ServicePerson();
		soapMessage = servicePerson.getPerson(person);

	}

	@Override
	public RouteBuilder createRouteBuilder() {
		return new RouteBuilder() {

			@Override
			public void configure() throws Exception {

				from("direct:start")
				
					.process(new Processor() {
	
						@Override
						public void process(Exchange exchange) throws Exception {
							System.out.println(exchange.getIn().getBody(String.class));
							exchange.getOut().setHeader(Exchange.CONTENT_TYPE, "text/xml");
							exchange.getOut().setHeader("SOAPAction", "person");
							exchange.getOut().setBody(exchange.getIn().getBody(String.class));
						}
					})
					
					.setHeader("CamelHttpMethod", constant("POST"))
					.to("http://localhost:9999/PersonMock")
						//.setBody(simple("${body}"))
				.end();

			}
		};

	}

	@Test
	public void testSuccess() throws Exception {

		context.start();

		template.sendBody("direct:start", soapMessage.getSOAPPart());
		assertMockEndpointsSatisfied();
	}

}
