package pacote.test;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import pacote.rest.MyRestRouteBuilder;

public class TesteRotaRest extends CamelTestSupport {

	@Override
	public RouteBuilder createRouteBuilder() {
		return new MyRestRouteBuilder() {
			@Override
			public void configure() {
				from("direct:start")
				.process(new Processor() {
					
					@Override
					public void process(Exchange exchange) throws Exception {
						System.out.println(exchange.getIn().getBody(String.class));
					}
				})
				.recipientList(simple("http://restcountries.eu/rest/v2/alpha/${body}?bridgeEndpoint=true&amp"));
			}

		};

	}

	@Test
	public void testIsCamelMessage() throws Exception {
		template.sendBody("direct:start", "bra");
		assertMockEndpointsSatisfied();
	}

}
