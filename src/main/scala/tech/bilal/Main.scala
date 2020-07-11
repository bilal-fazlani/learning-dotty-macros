package tech.bilal

import java.io.{File, PrintWriter}

import scala.io.Source

object Main  {
  def ePrintln(str: => String) = System.err.println(str)
  
  def writeFile(fileName:String, header:String, values:List[String]) = {
    val f = new File(fileName)
    val wr = new PrintWriter(f)
    wr.println(header)
    values.foreach(wr.println)
    wr.close()
  }

  def main (args: Array[String] ): Unit = {
    
    val exitCode = if (args.headOption.isEmpty) {
      ePrintln("please specify csv file")
      1
    }
    else if (!new File(args.head).exists()) {
      ePrintln("file not found")
      1
    }
    else {
      val csvFile = CSVFile(Source.fromFile(args.head))
      writeFile(fileName =  args.head+"_padded.csv", csvFile.title, csvFile.values)
      println("DONE")
      
      0
    }
    
    sys.exit(exitCode)
  }
}
