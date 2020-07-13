package bilal.tech.scala.csv

object Utils {
  def splitMore(string:String,separator: Char = ','): List[String] = {
    string.foldLeft(List(""))((acc, cur) => {
      cur match {
        case ',' => acc.appended("")
        case x => acc.take(acc.length - 1).appended(acc.last + x)
      }
    })
  }
}