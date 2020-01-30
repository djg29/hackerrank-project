package com.nike.interview.controller

import akka.Done
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import com.nike.interview.model.{Item, Order}
import com.nike.interview.repository.ItemRepository
import spray.json.DefaultJsonProtocol._
import scala.concurrent.Future

case class StockTradeApiController(repo: ItemRepository) {

  implicit val itemFormat = jsonFormat2(Item)
  implicit val orderFormat = jsonFormat1(Order)

  val route =
    concat(
      get {
        pathPrefix("item" / LongNumber) { id =>
          val maybeItem: Future[Option[Item]] = repo.fetchItem(id)

          onSuccess(maybeItem) {
            case Some(item) => complete(item)
            case None       => complete(StatusCodes.NotFound)
          }
        }
      },
      post {
        path("create-order") {
          entity(as[Order]) { order =>
            val saved: Future[Done] = repo.saveOrder(order)
            onComplete(saved) {
              _ => complete("order created")
            }
          }
        }
      }
    )


}

