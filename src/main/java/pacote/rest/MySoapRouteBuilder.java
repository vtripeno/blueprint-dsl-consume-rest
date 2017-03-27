package pacote.rest;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
//import org.apache.camel.dataformat.soap.SoapJaxbDataFormat;
//import org.apache.camel.dataformat.soap.name.ServiceInterfaceStrategy;

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
					person.setFirstName("TESTE");
					person.setLastName("TESTANDO");
					person.setAge(4000);
					
					exchange.getOut().setBody(servicePerson.getPerson(person));
				}
			})
			.log("Entrada Body ")
			.log("${body}")
//			.marshal(soapDF)
//			.marshal("soapjaxb")
			.to("http://localhost:9999/PersonMock")
				.setBody(simple("${body}"))
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