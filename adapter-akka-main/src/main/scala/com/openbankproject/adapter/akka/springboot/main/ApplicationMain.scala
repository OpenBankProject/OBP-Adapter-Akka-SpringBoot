package com.openbankproject.adapter.akka.springboot.main

import com.openbankproject.adapter.akka.commons.config.AkkaConfig
import com.openbankproject.adapter.akka.springboot.main.service.RestTransfer
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean

@SpringBootApplication
@EnableFeignClients
class ApplicationMain {

  @Bean(destroyMethod="terminateActorSystem")
  def akkaConfig(restTransfer: RestTransfer) = new AkkaConfig(restTransfer)
}

object ApplicationMain extends App {
  SpringApplication.run(classOf[ApplicationMain], args:_*)
}

/**
  * run as dev profile
  */
private object ApplicationDevMain extends App {
  System.setProperty("spring.profiles.active", "dev")
  SpringApplication.run(classOf[ApplicationMain], args:_*)
}
