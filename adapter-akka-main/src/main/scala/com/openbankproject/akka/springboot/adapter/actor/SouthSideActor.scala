package com.openbankproject.akka.springboot.adapter.actor

import java.util.Date

import akka.actor.Actor
import com.openbankproject.akka.springboot.adapter.service.{RestService, RestTransfer}
import com.openbankproject.commons.ExecutionContext.Implicits.global
import com.openbankproject.commons.model._
import com.openbankproject.utils.APIUtil
import javax.annotation.Resource
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component("SouthSideActor")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class SouthSideActor  extends Actor  {

  @Resource
  val restTransfer: RestTransfer = null

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
      restTransfer.transfer(outBound).foreach(self ! _)

    case _ => sender ! mockAdapterInfo
  }

}
  