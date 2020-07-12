package tech.bilal.csv.parse

import bilal.tech.csv.parse
import bilal.tech.csv.parse.{CSV, Decoder, ParseError}

import scala.io.Source

class MetaProgramming extends munit.FunSuite {
  test("should generate decoder for type and parse") {
    case class Sample(name:String, id:Int) derives Decoder
    val result = CSV.parse[Sample]("bilal,123")
    assertEquals(result, Right(Sample("bilal", 123)))
  }
  
  test("should generate decoder for type and fail to parse for invalid type") {
    case class Sample(name:String, id:Int) derives Decoder
    val result = CSV.parse[Sample]("bilal,123FFF")
    assertEquals(result, Left(ParseError(
      input = "123FFF",
      errorMessage = "value is not valid Int"
    )))
  }
}
