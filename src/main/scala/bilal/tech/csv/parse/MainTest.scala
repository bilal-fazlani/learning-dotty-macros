package bilal.tech.csv.parse

object MainTest extends App {
  case class A(i: Int, s: String) derives Decoder
  println(CSV.parse[A]("12,asd"))
}


