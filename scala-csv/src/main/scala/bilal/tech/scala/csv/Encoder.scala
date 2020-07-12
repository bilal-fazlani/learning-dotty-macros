package bilal.tech.scala.csv

trait Encoder[T] {
  def encode(obj:T):Either[CodecError, String]
}
