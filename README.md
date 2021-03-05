# ebiTest

BACKGROUND

This small repository contains REST web service that can be used to search gene names from public Ensembl database. The REST web service endpoint is called ‘gene_suggest’ and it requires the following three parameters:

query: the query typed by the user, e.g. brc
species: the name of the target species, e.g. homo_sapiens
limit: the maximum number of suggestions to return, e.g. 10
The endpoint returns list of suggested gene names for the given query and target species.

This exercise was done using Java, Maven Wrapper, Spring Boot ja IntelliJ IDEA. This is just a demo web service and there is no any authentication, authorization, IP filtering or logging. Error reporting is very basic and it does not contain proper Error -class.

USING AND TESTING

In order to use the web service, you have to do the following:

If not installed earlier, install Java Runtime (JRE) 11 or later. If you want to do further development (or for example configure embedded Tomcat) then you’ll need also JDK 11 or later.

Clone the repository using the following command: git clone https://github.com/kyostisutinen/ebiTest.git. Once you have cloned repository, go to root folder of ebiTest. In this folder you’ll see README.md -file and ebitest -subfolder.

Run .jar file that is in the repository using the following command: java -jar ebitest/target/ebitest-0.0.1-SNAPSHOT.jar --> This starts embedded Tomcat server (listening port 8080) and the REST web service will be accessible

Now, you can test access to the endpoint using browser (or curl or Postman or ...). You simply copy-paste the following url to browser's address bar http://localhost:8080/gene_suggest?query=bra&species=homo_sapiens&limit=3

You should get the following result: [ {"geneName":"BRAF","species":"homo_sapiens"}, {"geneName":"BRAFP1","species":"homo_sapiens"}, {"geneName":"BRAP","species":"homo_sapiens"} ]

In addition to the test above, you should always create your own REST api automated unit tests (for example using Postman or JUnit). These automated tests should be executed whenever you are finalizing changes to your application.

DEPLOYMENT

As mentioned earlier, this REST web service includes embedded Tomcat server. It’s using default Tomcat settings but default settings can be changed using application.properties file (see for example https://www.baeldung.com/spring-boot-configure-tomcat).

SCALING

If you need to scale up (or down) your REST web service, one solution is to use Docker containers. You simply create Docker image from Spring Boot (using Maven or Gradle). Once you have Docker image, you can orchestrate Docker containers for example using Kubernetes. But if you only need to improve performance a little bit then it may be enough if you focus on web server tuning.
