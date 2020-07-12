package bilal.tech.csv.parse

object CSV {
  def parse[T](value: String)(using dec:Decoder[T]): Either[ParseError, T] = 
    dec.decode(value)
}