package tech.bilal

import scala.io.{BufferedSource, Source}

case class CSVFile(title:String, values:List[String])

object CSVFile{
  def apply(input: Source): CSVFile = {
    val lines = input
      .getLines()
      .toList
    
    val values = lines
      .drop(1)
      .map(getData)
      .map(parse)
      .map(pad)
      .map(makeCsv)
      .toList

    val title = lines.head
    
    CSVFile(title, values)
  }
}


