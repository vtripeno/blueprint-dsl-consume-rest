# Camel DSL for a REST API - Demo
- Este é um projeto Camel que utiliza DSL para formar suas rotas;
- Para criar um projeto deste tipo selecione File > New > Fuse Integration Project;

## Breve Descrição
- Este projeto tem como objetivo demonstrar uma implementação com Camel DSL para consumo de uma API de terceiro;
- Neste exemplo a API consumida é a restcountries, que pode ser encontrada no site https://restcountries.eu;
- O serviço da API consumido é o serviço CODE que precisa do código do país para fazer a pesquisa, este código é definido de acordou com a ISO 3166-1 e pode possuir 2 ou 3 letras;
- Foi incrementado a este projeto mais uma rota que tem como objetivo fazer o consumo de um serviço exposto em um wsdl;
- Este wsdl pode ser encontrado na pasta wsdl/person.wsdl;
- Os resultados do consumo deste serviço exposto em wsdl foram dados mockados;
	
## Configurações Fuse
JBossFuse:karaf@root> features:install camel-jetty
JBossFuse:karaf@root> install -s mvn:org.apache.servicemix.bundle/org.apache.servicemix.bundle.saaj.imp/1.3.21_1 --> Mais informações sobre esta instalação na seção de Detalhes;

## Build
- Execução com Teste Unitário
	- mvn clean install
- Execução sem o Teste Unitário
	- mvn clean install -Dmaven.test.skip=true
-Execução somente do teste
	- mvn clean test

## Deploy
Por um aterfato do Maven
JBossFuse:karaf@root> osgi:install -s mvn:com.rest/camel-blueprint-consumo/1.0.0-SNAPSHOT
Output Console --> Bundle ID: <ID>

Pelo arquivo direto
JBossFuse:karaf@root> osgi:install -s file:///home/user/dev/camel-consumes-rest/target/camel-blueprint-consumo-SNAPSHOT.jar
Output Console --> Bundle ID: <ID>

## Undeploy
JBossFuse:karaf@root> osgi:uninstall <ID>

## Detalhes
- Para consumir a aplicação com chamda da API é necessário fazer uma chamada no endereço http://localhost:8980/ConsumoRest/{param},
sendo {param} o código do país a ser pesquisado, como por exemplo, bra - Brasil, col - Colombia;
- Para consumir a aplicação com chamda de SOAP é necessário fazer uma chamada no endereço http://localhost:8980/ConsumoSoap/SOAP (para bater no serviço mockado no SOAPUI) ou http://localhost:8980/ConsumoSoap/SOAP (para bater no serviço que está mockado em outtro bundle no servidor);
- Quando se trata de um consumo de SOAP, o servidor JBOSS possui um pequeno problema, pois o mesmo não possui as dependências necessárias para a execução de criação de mensagens SOAP, retornando o seguinte erro;
	javax.xml.soap.SOAPException: Unable to create MessageFactory: Provider for javax.xml.soap.MessageFactory cannot be found;
	- Ao procurar sobre o seguinte erro, foi possível encontrar no próprio site de respostas da Red Hat a seguinte resolução: 
		JBossFuse:karaf@root> install -s mvn:org.apache.servicemix.bundle/org.apache.servicemix.bundle.saaj.imp/1.3.21_1;
	- É possível encontrar o questionamento e a resposta para o erro na imagem que se encontra na pasta "print/instalacao para funcionamento de criacao mensagem soap.png" ou no link https://access.redhat.com/solutions/2268381;
