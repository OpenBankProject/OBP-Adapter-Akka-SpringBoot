package com.openbankproject.adapter.akka.commons

package object actor {
  implicit def toOption[T](any :T): Option[T] = Option(any)
}
