package com.openbankproject.akka.springboot.adapter

import akka.actor.{ActorRef, ActorSystem, Props}
import com.openbankproject.akka.springboot.adapter.actor.ResultActor
import com.openbankproject.akka.springboot.adapter.service.BankService
import javax.annotation.Resource
import org.junit.runner.RunWith
import org.junit.{Ignore, Test}
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(classOf[SpringRunner])
@SpringBootTest
@Ignore
class AkkaAdapterApplicationTest{
  @Resource
  val actorSystem: ActorSystem = null

  @Resource
  val bankService: BankService = null

  @Test
  def contextLoads ={
    val client:ActorRef = actorSystem.actorOf (Props.create (classOf[ResultActor]), "client")
    val actorSelection = actorSystem.actorSelection ("akka.tcp://SouthSideAkkaConnector_127-0-0-1@127.0.0.1:2662/user/akka-connector-actor")
    actorSelection.tell ("increase", client)
    actorSelection.tell ("increase", client)
    actorSelection.tell ("get", client)

    System.out.println ()
  }

  @Test
  def getBanksTest = {
    val banks = this.bankService.getBanks()
    val bank = this.bankService.getBankById("hello-bank-id")
    System.out.println(banks)
    System.out.println(bank)

  }
}
