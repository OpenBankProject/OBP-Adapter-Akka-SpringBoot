package com.openbankproject.akka.springboot.adapter.service

import com.openbankproject.commons.model.TopicTrait
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.{PathVariable, PostMapping, RequestBody}

@FeignClient(name="account", url="${adapter.remote.base.url}")
trait RestService {

  @PostMapping(value = Array("/{METHOD_NAME}"), consumes = Array("application/json"), produces = Array("application/json"))
  def sendOutboundInstance(@PathVariable("METHOD_NAME") methodName: String, @RequestBody outbound: TopicTrait): ResponseEntity[String]
}
