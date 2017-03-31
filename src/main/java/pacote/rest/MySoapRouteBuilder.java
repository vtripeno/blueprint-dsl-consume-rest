package pacote.rest;


import javax.xml.soap.SOAPMessage;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import pacote.apis.ServicePerson;
import pacote.contracts.Person;

public class MySoapRouteBuilder extends RouteBuilder {
	
	
	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub
		
//		SoapJaxbDataFormat soapDF = new SoapJaxbDataFormat("pacote.contracts.Person.ServicePerson", new ServiceInterfaceStrategy(ServicePerson.class, true));
		
		from("jetty:http://0.0.0.0:8980/ConsumoSoap/?matchOnUriPrefix=true")
			.routeId("mySoapRoute")
			.process(new Processor() {
				
				@Override
				public void process(Exchange exchange) throws Exception {
					// TODO Auto-generated method stub
					ServicePerson servicePerson = new ServicePerson();
					Person person = new Person();
					person.setId(1);
					person.setFirstName("TESTE");
					person.setLastName("TESTANDO");
					person.setAge(4000);
					
					SOAPMessage soapMessage = servicePerson.getPerson(person);
					
//					ByteArrayOutputStream out = new ByteArrayOutputStream();
//					soapMessage.writeTo(out);
					
					exchange.getOut().setHeader("caminho", exchange.getIn().getHeader(Exchange.HTTP_PATH, String.class));
					exchange.getOut().setBody(soapMessage);
//					exchange.getOut().setBody(out.toByteArray());
				}
			})
			.log("Entrada Body ")
			.log("${body}")
//			.marshal(soapDF)
//			.marshal("soapjaxb")
			.log("${header.caminho}")
			.log("caminho")
			.choice()
				.when(header("caminho").isEqualTo("SOAP"))
					.to("http://localhost:9999/PersonMock")
						.setBody(simple("${body}"))	
				.when(header("caminho").isEqualTo("Service"))	
					.to("http://localhost:9001/MyWebService")
						.setBody(simple("${body}"))
			.end()
			
//			.log("Saída Body ${body}")
			.process(new Processor() {
				
				@Override
				public void process(Exchange exchange) throws Exception {
					// TODO Auto-generated method stub
					String corpo = exchange.getIn().getBody(String.class);
					System.out.println("Body " + corpo);
					exchange.getOut().setBody(corpo);
				}
			})
			.transform(simple("${body}"))
			
			
		.end();
	}

}