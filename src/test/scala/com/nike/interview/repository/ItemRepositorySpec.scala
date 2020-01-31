package com.nike.interview.repository

import akka.Done
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.nike.interview.helper.Harness
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers
import org.scalatestplus.mockito.MockitoSugar

import scala.concurrent.ExecutionContext.Implicits.global

class ItemRepositorySpec extends AnyFunSpec
  with Matchers
  with ScalaFutures
  with MockitoSugar
  with SprayJsonSupport
  with Harness
  with IntegrationPatience {

  val repo = new ItemRepositoryImpl(){
    userMap = testUserMap
    tradeMap = testTrades
  }

  describe("Repository") {

    it("should return user trade details for valid user") {
      whenReady(repo.fetchItem(1)) { result =>
        assert(result === Some(List(trade1, trade2, trade3)))
      }
    }

    it("should not return for invalid user") {
      whenReady(repo.fetchItem(18)) { result =>
        assert(result === None)
      }
    }

    it("should create a transaction") {
      whenReady(repo.saveItem(newValidTrade)) { result =>
        assert(result === Done)
      }
    }

    it("should throw exception for duplicate transaction") {
      whenReady(repo.saveItem(duplicateTrade)) { result =>
        result mustBe a [DuplicateException]
      }
    }

  }

}
