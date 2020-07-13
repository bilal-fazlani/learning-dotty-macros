package bilal.tech.scala.csv

import bilal.tech.scala.csv.CodecError
import bilal.tech.scala.csv.CodecError.NoValue

import scala.Tuple$package.EmptyTuple
import scala.compiletime._
import scala.deriving.{Mirror, _}
import scala.quoted._

trait Decoder[T]{
  def decode(str:String):Either[CodecError, T]
}

object Decoder {
  def decoder[T:Type](f:Expr[String => Option[T]])(using qctx:QuoteContext): Expr[Decoder[T]] =  
    '{new Decoder[T] {
      override def decode(str: String): Either[CodecError, T] =
        str.trim match {
          case "" => Left(NoValue)
          case x => ${f}(x).toRight(CodecError(s"'$str' is not valid type"))
        }
    }}

  inline given stringDec as Decoder[String] = new Decoder[String] {
    override def decode(str: String): Either[CodecError, String] = Right(str)
  }
  
  inline given Decoder[Int] = ${decoder('{_.toIntOption})}
  inline given Decoder[Boolean] = ${decoder('{_.toBooleanOption})}
  inline given Decoder[Long] = ${decoder('{_.toLongOption})}
  inline given Decoder[Double] = ${decoder('{_.toDoubleOption})}
  
  inline given optionDec[T:Decoder] as Decoder[Option[T]] = new Decoder[Option[T]] {
    override def decode(str: String): Either[CodecError, Option[T]] =
      summon[Decoder[T]].decode(str) match {
        case Left(NoValue) => Right(None)
        case Left(e:CodecError) => Left(e)
        case Right("") => Right(None)
        case Right(v:T) => Right(Some(v))
      }
  }
  
  inline def derived[T](using m: Mirror.Of[T]): Decoder[T] = {
    val elemInstances = summonAll[m.MirroredElemTypes]
    inline m match {
      case p: Mirror.ProductOf[T] => productDecoder(p, elemInstances)
      case s: Mirror.SumOf[T]     => ???
    }
  }
  
  inline def summonAll[T <: Tuple]: List[Decoder[_]] = inline erasedValue[T] match {
    case EmptyTuple => Nil
    case _:(t *: ts) => summonInline[Decoder[t]] :: summonAll[ts]
  }

  def productDecoder[T](p: Mirror.ProductOf[T], elems: List[Decoder[_]]): Decoder[T] =
  new Decoder[T] {
    def decode(str: String): Either[CodecError, T] = {
      val strings = Utils.splitMore(str)
      if(strings.length < elems.length) Left(CodecError(
        s"""Record: '${str}'
          |does not have enough values
          |""".stripMargin))
      else elems.zip(strings)
        .map(_.decode(_).map(_.asInstanceOf[AnyRef]))
        .sequence
        .map(ts => p.fromProduct(new ArrayProduct(ts.toArray)))
    }
  }
    def [E,A](es: List[Either[E,A]]) sequence: Either[E,List[A]] =
  traverse(es)(identity)

  def traverse[E,A,B](es: List[A])(f: A => Either[E, B]): Either[E, List[B]] =
    es.foldRight[Either[E, List[B]]](Right(Nil))((h,tRes) => map2(f(h), tRes)(_ :: _))

  def map2[E, A, B, C](a: Either[E, A], b: Either[E, B])(f: (A, B) => C): Either[E, C] = 
    for { a1 <- a; b1 <- b } yield f(a1,b1)
}
