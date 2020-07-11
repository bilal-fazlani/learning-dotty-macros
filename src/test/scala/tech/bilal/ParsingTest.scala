package tech.bilal

class ParsingTest extends munit.FunSuite {
  test("get raw data"){
    val data = getData("1 A, 1, A")
    assertEquals(data, RawData("1 A", "1", "A"))
  }

  test("parse data"){
    val data = parse(RawData("1 A", "1", "A"))
    assertEquals(data, ParsedData(1, "A"))
  }
  
  test("pad data - 1 digit"){
    val data = pad(ParsedData(1, "A"))
    assertEquals(data, PaddedData("001 A", "001", "A"))
  }

  test("pad data - 2 digits"){
    val data = pad(ParsedData(31, "A"))
    assertEquals(data, PaddedData("031 A", "031", "A"))
  }

  test("pad data - 3 digits"){
    val data = pad(ParsedData(431, "A"))
    assertEquals(data, PaddedData("431 A", "431", "A"))
  }

  test("pad data - 4 digits"){
    val data = pad(ParsedData(6431, "A"))
    assertEquals(data, PaddedData("6431 A", "6431", "A"))
  }
  
  test("make csv"){
    val data = makeCsv(PaddedData("6431 A", "6431", "A"))
    assertEquals(data, "6431 A,6431,A")
  }
}
