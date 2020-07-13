package bilal.tech.scala.csv

sealed trait CodecError

object CodecError {
  case class CodecError1(errorMessage: String) extends CodecError
  case object NoValue extends CodecError
  def apply(errorMessage: String): CodecError = CodecError1(errorMessage)
}
