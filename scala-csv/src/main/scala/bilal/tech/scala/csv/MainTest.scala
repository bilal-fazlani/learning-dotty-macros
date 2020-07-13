package bilal.tech.scala.csv

object MainTest extends App {
  case class CaseClass(number: Option[Int], string: Option[String], boolean: Option[Boolean]) derives Decoder
  println(CSV.decode[CaseClass](", ,"))
}

/*
todo: Left(NoValue) -> Left(NoValue("no value found for foo.bar"))  
todo: support case class composition 
 */