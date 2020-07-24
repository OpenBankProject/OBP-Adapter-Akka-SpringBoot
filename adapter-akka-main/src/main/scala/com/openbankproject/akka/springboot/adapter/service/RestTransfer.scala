package com.openbankproject.akka.springboot.adapter.service

import com.openbankproject.commons.ExecutionContext.Implicits.global
import com.openbankproject.commons.dto.{InBoundTrait, OutInBoundTransfer}
import com.openbankproject.commons.model.TopicTrait
import com.openbankproject.commons.util.JsonSerializers
import javax.annotation.Resource
import net.liftweb.json
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

import scala.concurrent.Future
import scala.reflect.ManifestFactory


@Component
class RestTransfer extends OutInBoundTransfer {

  @Resource
  val restService: RestService = null

  private val ConnectorMethodRegex = "(?i)OutBound(.)(.+)".r

  private implicit val formats = JsonSerializers.nullTolerateFormats

  override def transfer(outbound: TopicTrait): Future[InBoundTrait[_]] = Future {
    val connectorMethod: String = outbound.getClass.getSimpleName match {
      case ConnectorMethodRegex(x, y) => s"${x.toLowerCase()}$y"
      case x => x
    }
    val outboundJson = json.compactRender(json.Extraction.decompose(outbound))
    val responseEntity: ResponseEntity[String] = restService.sendOutboundInstance(connectorMethod, outbound)
    if(responseEntity.getStatusCode.is2xxSuccessful()) {
      val inboundClass = Class.forName(s"com.openbankproject.commons.dto.InBound${connectorMethod.capitalize}")

      val inboundManifest = ManifestFactory.classType[InBoundTrait[_]](inboundClass)
      val jValue = json.parse(responseEntity.getBody)
      val inbound: InBoundTrait[_] = jValue.extract[InBoundTrait[_]](formats, inboundManifest)
      inbound
    } else {
      throw new RuntimeException(s"Request rest server fail, outbound instance: $outbound, statusCode: ${responseEntity.getStatusCodeValue}, body: ${responseEntity.getBody}")
    }
  }
}
