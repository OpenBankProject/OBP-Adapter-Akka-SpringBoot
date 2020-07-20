package com.openbankproject.akka.springboot.adapter.service

import com.openbankproject.akka.springboot.adapter.entity.BankAccount
import com.openbankproject.commons.model.{BankAccountCommons, BankCommons, CoreAccount}
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.{GetMapping, PathVariable}

import scala.tools.nsc.interpreter.JList

@FeignClient(name="account", url="${adapter.remote.base.url}")
trait BankService {

  @GetMapping(Array("banks/{BANK_ID}"))
  def getBankById(@PathVariable("BANK_ID") bankId: String): BankCommons


  @GetMapping(Array("/banks"))
  def getBanks(): List[BankCommons]


  @GetMapping(Array("/banks/{BANK_ID}/accounts"))
  def getAccounts(@PathVariable("BANK_ID") bankId :String): AccountResult
  
  @GetMapping(Array("/banks/{BANK_ID}/{USER_ID}/{ACCOUNT_ID}"))
  def getAccountById(@PathVariable("BANK_ID") bankId: String, @PathVariable("{USER_ID}") userId: String , @PathVariable("ACCOUNT_ID") accountId: String): BankAccountCommons

  @GetMapping(Array("/banks/{BANK_ID}/{ACCOUNT_ID}"))
  def getCoreBankAccount(@PathVariable("BANK_ID") bankId: String, @PathVariable("ACCOUNT_ID") accountId: String): CoreAccount
  
}

case class AccountResult(var accounts: JList[BankAccount])