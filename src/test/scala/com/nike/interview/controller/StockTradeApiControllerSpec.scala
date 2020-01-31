package com.nike.interview.controller

import akka.Done
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.http.scaladsl.util.FastFuture
import com.nike.interview.helper.Harness
import com.nike.interview.model.Trade
import com.nike.interview.repository.ItemRepository
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.when
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers
import org.scalatestplus.mockito.MockitoSugar
import com.nike.interview.repository.DuplicateException
import spray.json._
import DefaultJsonProtocol._

class StockTradeApiControllerSpec extends AnyFunSpec
  with Matchers
  with ScalaFutures
  with MockitoSugar
  with SprayJsonSupport
  with Harness
  with ScalatestRouteTest {

  val mockRepo = mock[ItemRepository]
  val controller = new StockTradeApiController(mockRepo)
  val routes = controller.route

  describe("StockTradeAPiController") {

    describe("getTradesRoute") {
      it("should return a transaction") {
        val request = HttpRequest(uri = "/trades/users/4")
        when(mockRepo.fetchItem(any[Int])).thenReturn(FastFuture.successful(Some(List(trade1))))

        request ~> routes ~> check {
          assert(status === StatusCodes.OK)
          entityAs[List[Trade]] mustBe List(trade1)
        }
      }

      it("should return not found for an invalid transaction") {
        val request = HttpRequest(uri = "/trades/users/3")
        when(mockRepo.fetchItem(any[Int])).thenReturn(FastFuture.successful(None))

        request ~> routes ~> check {
          assert(status === StatusCodes.NotFound)
        }
      }
    }

    describe("createTradeRoute") {
      it("should create a trade") {
        val entity = HttpEntity(contentType = ContentTypes.`application/json`, string = trade2.toJson.toString())
        val request = HttpRequest(uri = "/trades", method = HttpMethods.POST, entity = entity)
        when(mockRepo.saveItem(any[Trade])).thenReturn(FastFuture.successful(Done))

        request ~> routes ~> check {
          assert(status === StatusCodes.Created)
        }
      }

      it("should return 400 for duplicate trades") {
        val entity = HttpEntity(contentType = ContentTypes.`application/json`, string = trade2.toJson.toString())
        val request = HttpRequest(uri = "/trades", method = HttpMethods.POST, entity = entity)
        when(mockRepo.saveItem(any[Trade])).thenThrow(new DuplicateException)
        request ~> routes ~> check {
          assert(status === StatusCodes.BadRequest)
        }
      }
    }

  }

}

