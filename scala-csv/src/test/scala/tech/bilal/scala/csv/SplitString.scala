package tech.bilal.scala.csv

import bilal.tech.scala.csv.{CSV, CodecError, Decoder}
import bilal.tech.scala.csv.Utils
import scala.io.Source

class StringSplitTest extends munit.FunSuite {
  case class TestCase(input:String, output:List[String])
  val cases = List(
    TestCase("", List("")),
    TestCase(",",List("","")),
    TestCase(" , ",List(" "," ")),
    TestCase("a, ",List("a"," ")),
    TestCase("a, ,",List("a"," ","")),
    TestCase("a,,c",List("a","","c")),
    TestCase(",,",List("","","")),
    TestCase(",, ",List("",""," ")),
  )
  cases.foreach(testCase => test(s"'${testCase.input}' -> ${testCase.output}"){
    assertEquals(Utils.splitMore(testCase.input), testCase.output)
  })
}
