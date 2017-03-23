package pacote.teste.rest;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class MyRouteBuilder extends RouteBuilder {
	
	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub
		
		from("jetty:http://0.0.0.0:8980/ConsumoRest/?matchOnUriPrefix=true")
			.routeId("myRoute")
		
	//		.setHeader("country", simple("${country}", String.class))
	//		.setHeader("{country}",simple("{country}", String.class))
	//		.setHeader(Exchange.HTTP_QUERY).simple("/${country}")
			.process(new Processor() {
				
				@Override
				public void process(Exchange exchange) throws Exception {
					// TODO Auto-generated method stub
					System.out.println("HTTP_QUERY " + exchange.getIn().getHeader(Exchange.HTTP_QUERY, String.class));
					System.out.println("HTTP_RAW_QUERY " + exchange.getIn().getHeader(Exchange.HTTP_RAW_QUERY, String.class));
					System.out.println("CONTENT_TYPE " + exchange.getIn().getHeader(Exchange.CONTENT_TYPE, String.class));
					System.out.println("HTTP_URL " + exchange.getIn().getHeader(Exchange.HTTP_URL, String.class));
					System.out.println("FILE_PATH " + exchange.getIn().getHeader(Exchange.FILE_PATH, String.class));
					System.out.println("HTTP_PATH " + exchange.getIn().getHeader(Exchange.HTTP_PATH, String.class));
					System.out.println("HTTP_URI " + exchange.getIn().getHeader(Exchange.HTTP_URI, String.class));
					exchange.getOut().setHeader("country", exchange.getIn().getHeader(Exchange.HTTP_PATH, String.class));
					exchange.getOut().setBody(exchange.getIn().getHeader(Exchange.HTTP_METHOD, String.class));
				}
			})
	//		.to("http://restcountries.eu/rest/v2/all?bridgeEndpoint=true")
	//		.to("restlet:http://restcountries.eu/rest/v2/alpha/${header.country}?bridgeEndpoint=true")
	//		.to("http://restcountries.eu/rest/v2/alpha?codes=col;no;ee?bridgeEndpoint=true")
			
			/**
			 *  é necessário incluir o parâmetro ?bridgeEndpoint=true&amp; na url pertencente ao serviço rest
			 *  quando o mesmo deverá subir no bundle de jetty, pois senão o servidor não interpretará como uma
			 *  chamada a partir de um endereço
			 *  
			 */
			
			.doTry()
				.recipientList(simple("http://restcountries.eu/rest/v2/alpha/${header.country}?bridgeEndpoint=true&amp"))		
				.process(new Processor() {
					
					@Override
					public void process(Exchange exchange) throws Exception {
						// TODO Auto-generated method stub
						String corpoEntrada = exchange.getIn().getBody(String.class);
						System.out.println(corpoEntrada);
						exchange.getOut().setBody(corpoEntrada + " \nMensagem Adicionada ao Body");
					}
				})
				.log("${body}")
			.endDoTry()
			.doCatch(Exception.class)
				.process(new Processor() {
					
					@Override
					public void process(Exchange exchange) throws Exception {
						// TODO Auto-generated method stub
						exchange.getOut().setBody("Endereço pesquisado de forma incorreta");
					}
				})
				.log("${exception.message}")
			.end()
			
			.process(new Processor() {
				
				@Override
				public void process(Exchange exchange) throws Exception {
					// TODO Auto-generated method stub
					String corpoEntrada = exchange.getIn().getBody(String.class);
					System.out.println(corpoEntrada);
					exchange.getOut().setBody(corpoEntrada + " \nChamada pós try/catch, sem ser finally");
				}
			})
		.end();
	}

}
