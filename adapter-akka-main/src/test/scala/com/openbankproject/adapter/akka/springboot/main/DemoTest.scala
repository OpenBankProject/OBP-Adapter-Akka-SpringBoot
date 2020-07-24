package com.openbankproject.adapter.akka.springboot.main

import akka.actor.{ActorRef, ActorSelection, ActorSystem, Props}
import com.openbankproject.adapter.akka.commons.actor.SouthSideActor
import com.openbankproject.adapter.akka.springboot.main.service.RestTransfer
import com.typesafe.config.{ConfigFactory, ConfigValueFactory}
import org.junit.{Ignore, Test};

class DemoTest {

    @Test
    @Ignore
    def client()  = {
        val config = ConfigFactory.load.withValue("akka.remote.netty.tcp.port", ConfigValueFactory.fromAnyRef("2553"))
        val actorSystem:ActorSystem  = ActorSystem.create("akka_client", config)

        val client: ActorRef = actorSystem.actorOf(Props.create(classOf[SouthSideActor], new RestTransfer), "client");
        val actorSelection: ActorSelection = actorSystem.actorSelection("akka.tcp://SouthSideAkkaConnector_127-0-0-1@127.0.0.1:2662/user/akka-connector-actor");
        actorSelection.tell("increase", client);
        actorSelection.tell("get", client);
        actorSelection.tell(1, client);
        Thread.sleep(10000);
    }
}
