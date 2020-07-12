package bilal.tech.scala.csv

object CSV {
  def decode[T](value: String)(using dec:Decoder[T]): Either[CodecError, T] =
    dec.decode(value)
  def encode[T](obj:T)(using enc:Encoder[T]): Either[CodecError, String] =
    enc.encode(obj)
}