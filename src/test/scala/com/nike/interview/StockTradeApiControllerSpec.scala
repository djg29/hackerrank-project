package com.nike.interview

import akka.Done
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.http.scaladsl.util.FastFuture
import com.nike.interview.controller.StockTradeApiController
import com.nike.interview.model.{Item, Order}
import com.nike.interview.repository.ItemRepository
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito.when
import org.mockito.ArgumentMatchers.any

class StockTradeApiControllerSpec extends AnyFunSpec
  with Matchers
  with ScalaFutures
  with ScalatestRouteTest
  with MockitoSugar
  with SprayJsonSupport {

  val mockRepo = mock[ItemRepository]
  val controller = new StockTradeApiController(mockRepo)
  val routes = controller.route
  val anItem = Item("item1", 4L)
  val anOrderJson =
    """
      |{
      |	"items" : [{
      |		"name":"item1",
      |		"id": 1232
      |	}]
      |}
    """.stripMargin

  describe("StockTradeAPiController") {

    describe("getOrderRoute") {
      it("should return an order") {
        val request = HttpRequest(uri = "/item/4")
        when(mockRepo.fetchItem(any[Long])).thenReturn(FastFuture.successful(Some(anItem)))

        request ~> routes ~> check {
          assert(status === StatusCodes.OK)
        }
      }

      it("should return not found for an invalid order") {
        val request = HttpRequest(uri = "/item/4")
        when(mockRepo.fetchItem(any[Long])).thenReturn(FastFuture.successful(None))

        request ~> routes ~> check {
          assert(status === StatusCodes.NotFound)
        }
      }
    }

    describe(" createOrderRoute") {
      it("should return an order") {
        val entity = HttpEntity(contentType = ContentTypes.`application/json`, string = anOrderJson)
        val request = HttpRequest(uri = "/create-order", method = HttpMethods.POST, entity = entity)
        when(mockRepo.saveOrder(any[Order])).thenReturn(FastFuture.successful(Done))

        request ~> routes ~> check {
          assert(status === StatusCodes.OK)
        }
      }
    }

  }

}

