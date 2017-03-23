# Camel DSL for a REST API - Demo
- Este é um projeto Camel que utiliza DSL para formar suas rotas
- Para criar um projeto deste tipo tselecione File > New > Fuse Integration Project

## Breve Descrição
- Este projeto tem como objetivo demonstrar uma implementação com Camel DSL para consumo de uma API de terceiro.
- Neste exemplo a API consumida é a restcountries, que pode ser encontrada no site https://restcountries.eu
- O serviçoda API consumido é o serviço CODE que precisa do código do país para fazer a pesquisa, este código é definido
	de acordou com a ISO 3166-1 e pode possuir 2 ou 3 letras.
	
## Configurações Fuse
JBossFuse:karaf@root> features:install camel-jetty

## Build
- Execução com Teste Unitário
	- mvn clean install
- Execução sem o Teste Unitário
	- mvn clean install -Dmaven.test.skip=true
-Execução somente do teste
	- mvn clean test

## Deploy
Por um aterfato do Maven
JBossFuse:karaf@root> osgi:install -s mvn:com.mycompany/camel-blueprint/1.0.0-SNAPSHOT
Output Console --> Bundle ID: <ID>

Pelo arquivo direto
JBossFuse:karaf@root> osgi:install -s file:///home/user/dev/camel-consumes-rest/target/camel-blueprint-consumo-SNAPSHOT.jar
Output Console --> Bundle ID: <ID>

## Undeploy
JBossFuse:karaf@root> osgi:uninstall <ID>

## Detalhes
- Para consumir a aplicação é necessário fazer uma chamada no endereço http://localhost:8980/ConsumoRest/{param},
sendo {param} o código do país a ser pesquisado, como por exemplo, bra - Brasil, col - Colombia.



