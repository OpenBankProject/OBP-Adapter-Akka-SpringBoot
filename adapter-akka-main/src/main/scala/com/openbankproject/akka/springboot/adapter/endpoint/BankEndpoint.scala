package com.openbankproject.akka.springboot.adapter.endpoint

import io.swagger.annotations.Api
import org.springframework.web.bind.annotation._

@RestController
@RequestMapping(Array("v1/banks"))
@Api(tags = Array("banks operation."))
class BankEndpoint {

  @GetMapping(Array("/{BANK_ID}"))
  def getBankById(@PathVariable("BANK_ID") bankId :String) = s"bankId: $bankId"

}
