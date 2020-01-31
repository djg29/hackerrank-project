package com.nike.interview.model

import spray.json.{DefaultJsonProtocol, RootJsonFormat}

case class User(id:Int, name:String)

object User extends DefaultJsonProtocol {
  implicit val format: RootJsonFormat[User] = jsonFormat2(User.apply)
}
