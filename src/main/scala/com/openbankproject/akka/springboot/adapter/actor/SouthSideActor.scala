package com.openbankproject.akka.springboot.adapter.actor

import java.util.Date

import akka.actor.Actor
import com.openbankproject.akka.springboot.adapter.service.BankService
import com.openbankproject.commons.dto._
import com.openbankproject.commons.model.{InboundAccountCommons, _}
import com.openbankproject.utils.APIUtil
import javax.annotation.Resource
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

import scala.collection.immutable.List

@Component("SouthSideActor")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class SouthSideActor  extends Actor  {

  @Resource
  val bankService: BankService = null

  val mockAdapterInfo =
    s"""
       |{
       |  "name":"Akka adapter",
       |  "version":"vDec2018",
       |  "git_commit":"${APIUtil.gitCommit}",
       |  "date":"${new Date()}"
       |}
    """.stripMargin

  def receive = {
    case OutBoundGetBanks(InCC(inboundAdapterCallContext)) => sender ! InBoundGetBanks(inboundAdapterCallContext, Status("", Nil), bankService.getBanks())
    case OutBoundGetBank(InCC(inboundAdapterCallContext), bankId) => sender ! InBoundGetBank(inboundAdapterCallContext, Status("", Nil), this.bankService.getBankById(bankId.value))
    case OutBoundGetAdapterInfo(InCC(inboundAdapterCallContext)) => sender ! InBoundGetAdapterInfo(inboundAdapterCallContext, Status("", Nil), InboundAdapterInfoInternal("", Nil, "Adapter-Akka-SpringBoot", "vDec2018", APIUtil.gitCommit, new Date().toString))
    case OutBoundGetBankAccountsForUser(InCC(inboundAdapterCallContext), username) => sender ! InBoundGetBankAccountsForUser(
      inboundAdapterCallContext,
      Status("", Nil),
      List(InboundAccountCommons(
          bankId = "bankIdtob001",
          branchId = "thatBranch",
          accountId = "1",
          accountNumber = "",
          accountType = "",
          balanceAmount = "100",
          balanceCurrency = "EUR",
          owners = List("Simon"),
          viewsToGenerate = List("Owner", "Accountant", "Auditor"),
          bankRoutingScheme = "",
          bankRoutingAddress = "",
          branchRoutingScheme = "",
          branchRoutingAddress = "",
          accountRoutingScheme = "",
          accountRoutingAddress = ""
        )
      )
    )
      
    case OutBoundCheckBankAccountExists(outCC @InCC(inboundAdapterCallContext), bankId,accountId) =>
      sender ! InBoundCheckBankAccountExists(inboundAdapterCallContext, Status("", Nil) , bankService.getAccountById(bankId.value,
        outCC.outboundAdapterAuthInfo.flatMap(_.userId).orNull, accountId.value))

    case OutBoundGetCoreBankAccounts(outCC @InCC(inboundAdapterCallContext), bankIdAccountIds) =>
      val coreAccounts = bankIdAccountIds
          .map(it => bankService.getCoreBankAccount(it.bankId.value, it.accountId.value))
      sender ! InBoundGetCoreBankAccounts(inboundAdapterCallContext, Status("", Nil),
        coreAccounts)

    case _ => sender ! mockAdapterInfo

//    case OutBoundGetCoreBankAccounts(bankIdAccountIds, adapterCallContext) => sender ! InBoundGetCoreBankAccounts(getCoreBankAccountsAllBanks(bankIdAccountIds, adapterCallContext.get), adapterCallContext)
  }
  
//  def getCoreBankAccountsAllBanks(bankIdAccountIds: List[BankIdAccountId], adapterCallContext: AdapteradapterCallContext ) = {
//    bankIdAccountIds.flatMap( x => bankService.getCoreBankAccounts(x.bankId.toString(), adapterCallContext.get.userId.get ))
//  }

 
   object InCC{
     def unapply(outboundAdapterCallContext: OutboundAdapterCallContext): Option[InboundAdapterCallContext] =
       Some(InboundAdapterCallContext(correlationId = outboundAdapterCallContext.correlationId, sessionId = outboundAdapterCallContext.sessionId))
   }
}
  