package com.openbankproject.akka.springboot.adapter

import akka.actor.{ActorRef, ActorSystem, Props}
import com.github.tomakehurst.wiremock.junit.WireMockRule
import com.openbankproject.akka.springboot.adapter.actor.ResultActor
import com.openbankproject.akka.springboot.adapter.service.RestService
import javax.annotation.Resource
import org.junit.runner.RunWith
import org.junit.{After, Before, Ignore, Rule, Test}
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import com.github.tomakehurst.wiremock.client.WireMock._
import com.openbankproject.commons.dto.OutBoundGetBanks
import com.openbankproject.commons.util.JsonSerializers
import net.liftweb.json
import org.springframework.http.ResponseEntity

@RunWith(classOf[SpringRunner])
@SpringBootTest
@Ignore
class AkkaAdapterApplicationTest{
  @Resource
  val actorSystem: ActorSystem = null

  @Resource
  val restService: RestService = null
  @Value("${adapter.remote.base.url}")
  val baseUrl: String = null

  private var wireMockRule: WireMockRule = _

  @Before
  def startServer(): Unit = {
    val port = baseUrl.replaceFirst(""".+:(\d+)""", "$1").toInt
    wireMockRule = new WireMockRule(port)
    stubFor(post(urlEqualTo("/connector/getBanks"))
      .willReturn(aResponse()
        .withStatus(200)
        .withHeader("Content-Type", "application/json")
        .withBody(
          """
            |{
            |     "inboundAdapterCallContext": {
            |          "correlationId": "1flssoftxq0cr1nssr68u0mioj"
            |     },
            |     "status": {
            |          "errorCode": "",
            |          "backendMessages": []
            |     },
            |     "data": [
            |          {
            |               "bankId": {
            |                    "value": "gh.29.uk"
            |               },
            |               "shortName": "bank shortName string",
            |               "fullName": "bank fullName string",
            |               "logoUrl": "bank logoUrl string",
            |               "websiteUrl": "bank websiteUrl string",
            |               "bankRoutingScheme": "BIC",
            |               "bankRoutingAddress": "GENODEM1GLS"
            |          }
            |     ]
            |}
            |""".stripMargin)));
  }

  @After
  def stopServer(): Unit = {
    wireMockRule.stop()
  }


  @Test
  def contextLoads ={
    val client:ActorRef = actorSystem.actorOf (Props.create (classOf[ResultActor]), "client")
    val actorSelection = actorSystem.actorSelection ("akka.tcp://SouthSideAkkaConnector_127-0-0-1@127.0.0.1:2662/user/akka-connector-actor")
    actorSelection.tell ("increase", client)
    actorSelection.tell ("increase", client)
    actorSelection.tell ("get", client)

    println()
  }

  @Test
  def getBanksTest = {
    implicit val formats = JsonSerializers.nullTolerateFormats
    val outboundJson =
      """
        |{
        |       "outboundAdapterCallContext":{
        |         "correlationId":"1flssoftxq0cr1nssr68u0mioj"
        |       }
        |     }
        |""".stripMargin
//    val value: ResponseEntity[String] = restService.sendOutboundInstance("getBanks", outboundJson)
//    val banks = this.bankService.getBanks()
//    val bank = this.bankService.getBankById("hello-bank-id")
//    println(banks)
//    println(bank)
    println()
  }
}
