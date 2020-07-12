package bilal.tech.scala.csv

object MainTest extends App {
  case class A(i: Int, s: String) derives Decoder
  println(CSV.decode[A]("12,asd"))
}


