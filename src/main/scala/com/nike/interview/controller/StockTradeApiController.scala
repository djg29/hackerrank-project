package com.nike.interview.controller

import akka.Done
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.{ExceptionHandler, Route}
import com.nike.interview.model.{Trade, User}
import com.nike.interview.repository.{DuplicateException, ItemRepository}
import spray.json.DefaultJsonProtocol._

import scala.concurrent.Future
import scala.util.{Failure, Success}

case class StockTradeApiController(repo: ItemRepository) {

  //implicit val userFormat = jsonFormat2(User)
  //implicit val tradeFormat = jsonFormat7(Trade)

  implicit def myExceptionHandler: ExceptionHandler =
    ExceptionHandler {
      case _: DuplicateException => complete(StatusCodes.BadRequest)
    }

  val route =
    Route.seal(
      concat(
        get {
          pathPrefix("trades") {
            concat(
              path("users" / IntNumber) { userId =>
                val transactions: Future[Option[List[Trade]]] = repo.fetchItem(userId)

                onSuccess(transactions) {
                  case Some(list) => complete(list)
                  case _ => complete(StatusCodes.NotFound)
                }
              }
            )
          }
        },
        post {
          path("trades") {
            entity(as[Trade]) { transcation =>
              val saved: Future[Done] = repo.saveItem(transcation)
              onComplete(saved) {
                case Success(u) => {
                  complete(StatusCodes.Created)
                }
                case Failure(err) => complete(StatusCodes.BadRequest)
              }
            }
          }
        }
      )
    )


}

