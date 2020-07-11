package tech.bilal

def getData(str : String) = str.split(",").toList match {
  case display :: code :: name :: Nil => RawData(display.trim, code.trim, name.trim)
}

def parse(rec: RawData) =
  ParsedData(rec.code.trim.toInt, rec.name.trim)

type Padding = 2 | 3 | 4 | 5

def pad(data: ParsedData, padding: Padding) =
  PaddedData(
    s"${s"%0${padding}d".format(data.code)} ${data.name.trim}",
    data.code.toString,
    data.name
  )

def makeCsv(paddedData: PaddedData)= s"${paddedData.display},${paddedData.code},${paddedData.name}"