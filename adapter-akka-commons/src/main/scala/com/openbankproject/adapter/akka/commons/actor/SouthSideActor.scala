package com.openbankproject.adapter.akka.commons.actor

import java.util.Date

import akka.actor.Actor
import com.openbankproject.commons.ExecutionContext.Implicits.global
import com.openbankproject.commons.dto.OutInBoundTransfer
import com.openbankproject.commons.model._
import com.openbankproject.adapter.akka.commons.utils.APIUtil

class SouthSideActor(outInBoundTransfer: OutInBoundTransfer) extends Actor  {

  val mockAdapterInfo =
    s"""
       |{
       |  "name":"Akka adapter",
       |  "version":"Dec2018",
       |  "git_commit":"${APIUtil.gitCommit}",
       |  "date":"${new Date()}"
       |}
    """.stripMargin

  def receive = {
    case outBound: TopicTrait =>
      // sender in a future, must in the follow way: hold the sender reference, and use it in the future callback.
      val self = sender
      outInBoundTransfer.transfer(outBound).foreach(self ! _)

    case _ => sender ! mockAdapterInfo
  }

}
  