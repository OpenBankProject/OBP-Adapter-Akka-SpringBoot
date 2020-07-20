package com.openbankproject.akka.springboot.adapter

package object actor {
  implicit def toOption[T](any :T): Option[T] = Option(any)
}
