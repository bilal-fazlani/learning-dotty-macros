package tech.bilal

def getData(str : String) = str.split(",").toList match {
  case display :: code :: name :: Nil => RawData(display.trim, code.trim, name.trim)
}

def parse(rec: RawData) =
  ParsedData(rec.code.trim.toInt, rec.name.trim)

def pad(data: ParsedData) =
  PaddedData(
    s"${"%03d".format(data.code)} ${data.name.trim}",
    data.code.formatted("%03d"),
    data.name
  )

def makeCsv(paddedData: PaddedData)= s"${paddedData.display},${paddedData.code},${paddedData.name}"