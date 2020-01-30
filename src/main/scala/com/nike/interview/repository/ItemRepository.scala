package com.nike.interview.repository

import akka.Done
import com.nike.interview.model.{Item, Order}
import scala.concurrent.{ExecutionContext, Future}

trait ItemRepository {
  var orders: List[Item] = Nil

  def fetchItem(itemId: Long): Future[Option[Item]]

  def saveOrder(order: Order): Future[Done]

}

class ItemRepositoryImpl(implicit ec: ExecutionContext) extends ItemRepository {

  def fetchItem(itemId: Long): Future[Option[Item]] = Future {
    orders.find(o => o.id == itemId)
  }

  def saveOrder(order: Order): Future[Done] = {
    orders = order match {
      case Order(items) => items ::: orders
      case _            => orders
    }
    Future { Done }
  }

}

