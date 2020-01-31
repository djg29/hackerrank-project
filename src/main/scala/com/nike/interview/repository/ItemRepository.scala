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

  def fetchItem(userId: Int): Future[Option[List[Trade]]] = Future {
    val trades = userMap.get(userId)
    Thread.sleep(1000)
    trades.map(i => i.flatMap(id => tradeMap.get(id)))
  }

  def saveItem(trade: Trade): Future[Done] = {
    println(userMap)
    println(tradeMap)
    val tradeIds = userMap.get(trade.user.id)
    println(tradeIds)
    val isDuplicate = tradeIds.map(_.contains(trade.id))
    val result = isDuplicate match {
      case Some(x) => if(x) throw new DuplicateException else {
        tradeMap.put(trade.id, trade)
        trade.id :: tradeIds.get
      }
      case None => {
        tradeMap.put(trade.id, trade)
        trade.id :: Nil
      }
    }
    userMap.put(trade.user.id, result)
    Thread.sleep(1000)
    Future { Done }
  }

}

class DuplicateException extends RuntimeException

