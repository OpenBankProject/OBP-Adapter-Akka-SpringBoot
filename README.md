Welcome to the Obp-Akka-Adapter-SpringBoot
===============================

# ABOUT

This application is a implementation of adapter, transform with obp-api by akka, and get data from remote rest api endpoints, it use Spring Boot framework and scala language.



# LICENSE

This project is licensed under the AGPL V3 (see NOTICE) and a commercial license from TESOBE.

# SETUP

The project is using Maven 3 as a build tool.
Se pom.xml respectively for the dependencies.
This project should compile and run with JDK 1.8, OBP-API can be any JDK version from 1.8 to 13.
--
## install
* clone the OBP-API project develop branch: [OBP-API](https://github.com/OpenBankProject/OBP-API.git), run command: 
```
~/OBP-API $ mvn clean install -pl .,obp-commons
```
* follow the README.md of OBP-API to initiate project, and modify the default.props file in OBP-API should have the follow settings
```
connector=akka_vDec2018
akka_connector.hostname=127.0.0.1
akka_connector.port=2662
```

* install spring boot cli [spring boot cli](https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started-installing-spring-boot.html#getting-started-installing-the-cli)
* start mock remote server:
```
~/OBP-Adapter-Akka-SpringBoot $ spring  run src/test/resources/RemoteEndpoints.groovy -- --server.port=9094
```
* start adapter by call main class: com.openbankproject.adapter.ApplicationDevMain
or by command, now only "Get Bank" and "Get Banks" endpoints works.
in the project folder, execute follow command to package and start project:

```
~/OBP-Adapter-Akka-SpringBoot $ mvn package
~/OBP-Adapter-Akka-SpringBoot $ java -jar target/adapter-akka-springboot-1.1.0.jar
```
* the adapter is ready, you can call get banks and get bank by id from obp explorer.