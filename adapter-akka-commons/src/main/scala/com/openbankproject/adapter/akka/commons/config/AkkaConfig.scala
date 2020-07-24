package com.openbankproject.adapter.akka.commons.config

import akka.actor.{ActorSystem, Props}
import com.openbankproject.adapter.akka.commons.actor.SouthSideActor
import com.openbankproject.commons.dto.OutInBoundTransfer
import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps


case class AkkaConfig(outInBoundTransfer: OutInBoundTransfer, actorSystemOpt: Option[ActorSystem] = None) {

  val logger = LoggerFactory.getLogger(classOf[AkkaConfig])
  val SYSTEM_NAME = "SouthSideAkkaConnector_"

  val actorSystem: ActorSystem  = {
    val loadConfig = ConfigFactory.load
    val hostname = loadConfig.getString("akka.remote.netty.tcp.hostname")
    val system = actorSystemOpt.getOrElse {
      ActorSystem.create(SYSTEM_NAME + hostname.replace('.', '-'), loadConfig)
    }
    system.actorOf(Props.create(classOf[SouthSideActor], outInBoundTransfer), "akka-connector-actor")

    system
  }

  def terminateActorSystem(): Unit = {
    val actorSystem = this.actorSystem
     logger.info("start terminate actorSystem")
    Await.ready(actorSystem.terminate, 1 minutes )
    logger.info("finished terminate actorSystem")
  }
}
