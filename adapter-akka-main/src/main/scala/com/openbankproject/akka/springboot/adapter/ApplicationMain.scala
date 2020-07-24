package com.openbankproject.akka.springboot.adapter

import com.openbankproject.akka.springboot.adapter.config.AkkaConfig
import com.openbankproject.akka.springboot.adapter.service.RestTransfer
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
