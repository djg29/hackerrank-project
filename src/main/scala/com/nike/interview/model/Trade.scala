package com.nike.interview.model

import java.time.OffsetDateTime

import spray.json.{DefaultJsonProtocol, RootJsonFormat}

case class Trade(id:Int, transactionType: String, user:User, symbol: String, shares:Int, price:Double, timestamp:String)

object Trade extends DefaultJsonProtocol {
  implicit val format: RootJsonFormat[Trade] = jsonFormat7(Trade.apply)
}
