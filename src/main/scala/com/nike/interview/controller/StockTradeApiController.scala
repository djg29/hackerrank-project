package com.nike.interview.controller

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import com.nike.interview.model.Trade
import com.nike.interview.repository.ItemRepository

case class StockTradeApiController(repo: ItemRepository) {

  val route = concat(
    get {
      pathPrefix("trades") {
        concat(
          path("users" / IntNumber) { userId =>
            complete(StatusCodes.NotFound)
          }
        )
      }
    },
    post {
      path("trades") {
        entity(as[Trade]) { transcation =>
          complete(StatusCodes.NotFound)
        }
      }
    }
  )


}

