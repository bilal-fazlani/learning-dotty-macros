package tech.bilal.scala.csv

import bilal.tech.scala.csv.{CSV, CodecError, Decoder}

import scala.io.Source

class MetaProgramming extends munit.FunSuite {
  test("should generate decoder for type and parse") {
    case class Sample(name:String, id:Int) derives Decoder
    val result = CSV.decode[Sample]("bilal,123")
    assertEquals(result, Right(Sample("bilal", 123)))
  }
  
  test("should generate decoder for type and fail to parse for invalid type") {
    case class Sample(name:String, id:Int) derives Decoder
    val result = CSV.decode[Sample]("bilal,123FFF")
    assertEquals(result, Left(CodecError(
      errorMessage = "value is not valid Int"
    )))
  }

  test("should generate decoder for type and fail to parse for invalid type") {
    case class Sample(name:String, id:Int) derives Decoder
    val result = CSV.decode[Sample]("bilal,123FFF")
    assertEquals(result, Left(CodecError(
      errorMessage = "value is not valid Int"
    )))
  }
}
