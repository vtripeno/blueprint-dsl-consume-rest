package pacote.test;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import pacote.rest.MyRestRouteBuilder;

public class TesteRotaRest extends CamelTestSupport {

	@Override
	public RouteBuilder createRouteBuilder() {
		return new MyRestRouteBuilder() {
			@Override
			public void configure() {
				from("direct:start").to("mock:test");
			}

		};

	}

	@Test
	public void testIsCamelMessage() throws Exception {
		MockEndpoint mock = getMockEndpoint("mock:test");
		mock.expectedMessageCount(2);
		mock.message(0).body().contains("Camel");
		mock.message(1).body().contains("Camel");
		template.sendBody("direct:start", "Hello Camel");
		template.sendBody("direct:start", "Camel rocks");
		assertMockEndpointsSatisfied();
	}

}
