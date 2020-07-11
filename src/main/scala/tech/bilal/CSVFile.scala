package tech.bilal

import scala.io.{BufferedSource, Source}

case class CSVFile(title: String, values: List[String])

object CSVFile {
  def apply(input: Source): CSVFile = {
    val lines = input
      .getLines()
      .toList

    val padF = pad(_, 3)
    
    val values = lines
      .drop(1)
      .map(getData)
      .map(parse)
      .map(padF)
      .map(makeCsv)
      .toList

    val title = lines.head

    CSVFile(title, values)
  }
}
