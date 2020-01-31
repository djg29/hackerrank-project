package com.nike.interview.helper

import com.nike.interview.model.{Trade, User}

import scala.collection.mutable

trait Harness {

  val testUserMap: mutable.HashMap[Int, List[Int]] = collection.mutable.HashMap(1 -> List(3, 76, 8), 2 -> List(87, 99, 23), 5 -> List(77, 35, 17))

  val aUser = User(name = "tester1", id = 1)
  val anotherUser = User(name = "tester2", id = 2)
  val yetAnotherUser = User(name = "tester3", id = 5)

  val trade1 = Trade(id = 3, transactionType = "BUY", user = aUser, symbol = "AAA", shares = 2, price = 98.97, timestamp = "")
  val trade2 = Trade(id = 76, transactionType = "SELL", user = aUser, symbol = "BBB", shares = 8, price = 8.97, timestamp = "")
  val trade3 = Trade(id = 8, transactionType = "BUY", user = aUser, symbol = "TTT", shares = 10, price = 76.97, timestamp = "")
  val trade4 = Trade(id = 87, transactionType = "SELL", user = anotherUser, symbol = "AAA", shares = 45, price = 6.76, timestamp = "")
  val trade5 = Trade(id = 99, transactionType = "BUY", user = anotherUser, symbol = "GGG", shares = 26, price = 45.23, timestamp = "")
  val trade6 = Trade(id = 23, transactionType = "SELL", user = anotherUser, symbol = "TTT", shares = 32, price = 76.97, timestamp = "")
  val trade7 = Trade(id = 77, transactionType = "SELL", user = aUser, symbol = "TTT", shares = 12, price = 21.97, timestamp = "")
  val trade8 = Trade(id = 35, transactionType = "SELL", user = aUser, symbol = "KKK", shares = 77, price = 71.97, timestamp = "")
  val trade9 = Trade(id = 17, transactionType = "SELL", user = aUser, symbol = "BBB", shares = 87, price = 58.97, timestamp = "")

  val newValidTrade = Trade(id = 543, transactionType = "SELL", user = aUser, symbol = "JHG", shares = 37, price = 873.56, timestamp = "")
  val duplicateTrade = Trade(id = 17, transactionType = "SELL", user = aUser, symbol = "BBB", shares = 87, price = 58.97, timestamp = "")

  val testTrades: mutable.HashMap[Int, Trade] = collection.mutable.HashMap(
    3 -> trade1,
    76 -> trade2,
    8 -> trade3,
    87 -> trade4,
    99 -> trade5,
    23 -> trade6,
    77 -> trade7,
    35 -> trade8,
    17 -> trade9
  )

}
