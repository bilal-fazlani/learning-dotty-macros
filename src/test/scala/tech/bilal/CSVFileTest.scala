package tech.bilal

import scala.io.Source

class CSVFileTest extends munit.FunSuite {
  test("generate padded csv data") {
    val inputContents =
      """display, code, name
        |1 A, 1, A
        |67 B , 67, B
        |5452 C, 5452, C""".stripMargin
    val source: Source = Source.fromIterable(inputContents)
    val csv = CSVFile(source)
    assertEquals(csv, CSVFile("display, code, name", List(
      "001 A,1,A",
      "067 B,67,B",
      "5452 C,5452,C"
    )))
  }
}
