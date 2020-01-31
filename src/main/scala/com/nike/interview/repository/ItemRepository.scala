package com.nike.interview.repository

import akka.Done
import com.nike.interview.model.Trade

import scala.collection.mutable
import scala.concurrent.{ExecutionContext, Future}

trait ItemRepository {
  // in memory data storage
  var userMap: mutable.HashMap[Int, List[Int]] = new mutable.HashMap[Int, List[Int]]()
  var tradeMap: mutable.HashMap[Int, Trade] = new mutable.HashMap[Int, Trade]()

  def fetchItem(userId: Int): Future[Option[List[Trade]]]

  def saveItem(trade: Trade): Future[Done]
}

class ItemRepositoryImpl(implicit ec: ExecutionContext) extends ItemRepository {

  def fetchItem(userId: Int): Future[Option[List[Trade]]] = ???

  def saveItem(trade: Trade): Future[Done] = ???

}

class DuplicateException extends RuntimeException

