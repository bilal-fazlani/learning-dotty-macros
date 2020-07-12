package tech.bilal.csv.padding

case class PaddedData(display:String, code:String, name:String){
  override def toString: String = s"[${display}]${" ".*(50-display.length)}[${code}]${" ".*(8-code.length)}[${name}]"
}

case class ParsedData(code:Int, name:String)

case class RawData(display:String, code:String, name:String)
