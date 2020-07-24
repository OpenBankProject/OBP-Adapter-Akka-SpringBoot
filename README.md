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
* modify application.yaml, the `adapter.remote.base.url` value should be any OBP-API ConnectorEndpoints url: 
```
adapter:
  # set url to OBP-API ConnectorEndpoints url, note: in OBP-API props should have settings: connector.name.export.as.endpoint=mapped
  remote.base.url: http://127.0.0.1:8080/connector
```
* follow the README.md of OBP-API to initiate project, and modify the default.props file in OBP-API should have the follow settings
```
connector=akka_vDec2018
akka_connector.hostname=127.0.0.1
akka_connector.port=2662
```

* start adapter by call main class: com.openbankproject.adapter.akka.springboot.main.ApplicationDevMain
or by command.
in the project folder, execute follow command to package and start project:

```
~/OBP-Adapter-Akka-SpringBoot $ mvn install -pl .,adapter-akka-commons && mvn package
~/OBP-Adapter-Akka-SpringBoot $ java -jar adapter-akka-main/target/adapter-akka-main-1.1.0.jar
```
* the adapter is ready, you can call get banks and get bank by id from obp explorer.