package pacote.test;

import javax.xml.soap.SOAPMessage;

import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.apache.camel.Predicate;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import pacote.apis.ServicePerson;
import pacote.contracts.Person;
import pacote.mock.ServiceResponsePerson;

public class TesteRotaSoap extends CamelTestSupport {

	SOAPMessage soapMessage;
	Person person;
	ServicePerson servicePerson;
	ServiceResponsePerson serviceResponsePerson;
	MockEndpoint resultEndpoint;
	SOAPMessage soapResponse;
	
	@Before
	public void initMock() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		person = new Person();
		person.setFirstName("TESTE");
		person.setLastName("TESTANDO");
		person.setAge(4000);

		servicePerson = new ServicePerson();
		soapMessage = servicePerson.getPerson(person);
		
		serviceResponsePerson = new ServiceResponsePerson();
		
		soapResponse =  serviceResponsePerson.getResponsePerson(soapMessage);
		
//		resultEndpoint = context.resolveEndpoint("mock:foo", MockEndpoint.class);
		
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
					//.to("http://localhost:9999/PersonMock")
						//.setBody(simple("${body}"))
					
//					.process(new Processor() {
//						
//						@Override
//						public void process(Exchange exchange) throws Exception {
//							// TODO Auto-generated method stub
//							soapResponse =  serviceResponsePerson.getResponsePerson(soapMessage);
//							exchange.getOut().setBody(soapResponse.getSOAPPart());
//							System.out.println(exchange.getOut().getBody(String.class));
//						}
//					})
					.to("mock:result/PersonMock")
				.end();

			}
		};

	}

	/*@Test
	public void testSuccess() throws Exception {

		context.start();

		template.sendBody("direct:start", soapMessage.getSOAPPart());
		MockEndpoint mockEndpoint = getMockEndpoint("mock:result");
		mockEndpoint.expectedMessageCount(1);
		mockEndpoint.message(0).body().contains(soapResponse.getSOAPPart());
//		System.out.println(soapResponse.getSOAPPart());
		assertMockEndpointsSatisfied();
	}*/
	
	@Test
	public void testGetActionRoute() throws Exception {
	    MockEndpoint greeterService = getMockEndpoint("mock:result/PersonMock");

	    greeterService.returnReplyBody(new Expression() {
	        @SuppressWarnings("unchecked")
			@Override
	        public <T> T evaluate(Exchange exchange, Class<T> type) {
	        	exchange.getOut().setBody(soapResponse.getSOAPPart());
	        	System.out.println(exchange.getOut().getBody(String.class));
	            return (T) soapResponse;
	        }
	    });

	    greeterService.expectedMessagesMatches(new Predicate() {

	        @Override
	        public boolean matches(Exchange exchange) {
	            //assertEquals("U.S.", exchange.getIn().getBody(String.class));
//	        	System.out.println(exchange.getIn().getBody(String.class));
	            return true;
	        }
	    });
	    template.sendBody("direct:start", soapMessage.getSOAPPart());


	    greeterService.assertIsSatisfied();
	}

}
