package pacote.teste.test;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import pacote.teste.rest.MyRouteBuilder;

public class TesteRota extends CamelTestSupport {

	@Override
	public RouteBuilder createRouteBuilder() {
		return new MyRouteBuilder() {
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
