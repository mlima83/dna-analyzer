# dna-analyzer
Essa API é responsável por fornecer uma análise de DNA fornecido, identificando se é um Simiano ou Humano.


#### Tecnologias utilizadas

 * Java 1.8
 * Apache maven 3.6.2
 * Springboot 2.1.6.RELEASE
 * Springfox Swagger 2 2.9.2
 * Junit 4.12
 * Mockito 2.23
 * h2database 1.4 

#### Pré-requisitos antes de executar a aplicação 

 * Ter previamente instalado e configurado o Git, Java e Maven nas versões citas acima.
  
 * Fazer clone deste repositório em seu workspace.
 
 * Após fazer o clone do projeto navege até o diretório principal da aplicação:
 
	$ cd ~/dna-analyser 

 * Execute o seguinte commando para o maven baixar as dependências do projeto e compilar o jar:

	$ mvn install
 
#### Executando a aplicação com maven

 *  Para iniciar a aplicação com o maven execute esse comando:
 
	$ mvn spring-boot:run
	
 * A aplicação estará rodando na porta 3000 e poderá acessá-la facilmente pela interface do swagger2 no endereço: 
 
[http://localhost:3000/swagger-ui.html](http://localhost:3000/swagger-ui.html)

#### Executando a aplicação com Docker

 * Deve ter o Docker previamente instalado e configurado.
	
 * Faça o build da imagem docker do projeto:
 
	$ docker build -t dna-analyser .
	
 * Para iniciar a aplicação com o docker execute o seguinte comando:
 
	$ docker run -p 3000:3000 -t dna-analyser
	
	
#### Acesso ao servidor para teste

 * [Inteface swagger - http://104.155.157.148/swagger-ui.html](http://104.155.157.148/swagger-ui.html)
 * [Endpoint /simian - http://104.155.157.148/simian](http://104.155.157.148/simian)
 * [Endpoint /stats - http://104.155.157.148/stats](http://104.155.157.148/stats)
	

#### Referências

 * [DDD - https://martinfowler.com/bliki/BoundedContext.html](https://martinfowler.com/bliki/BoundedContext.html)
 * [Spring data - https://spring.io/guides/gs/accessing-data-jpa](https://spring.io/guides/gs/accessing-data-jpa)
 * [Spring with docker - https://spring.io/guides/gs/spring-boot-docker](https://spring.io/guides/gs/spring-boot-docker/)
	 
