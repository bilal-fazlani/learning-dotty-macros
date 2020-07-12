package bilal.tech.scala.csv

import scala.Tuple$package.EmptyTuple
import scala.deriving._
import scala.compiletime._
import scala.deriving.Mirror
import scala.quoted._

trait Decoder[T]{
  def decode(str:String):Either[CodecError, T]
}

object Decoder {
  inline given stringDec as Decoder[String] = new Decoder[String] {
    override def decode(str: String): Either[CodecError, String] = Right(str)
  }

  inline given intDec as Decoder[Int] = new Decoder[Int] {
    override def decode(str: String): Either[CodecError, Int] =
      str.toIntOption.toRight(CodecError("value is not valid Int"))
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
      elems.zip(str.split(','))
        .map(_.decode(_).map(_.asInstanceOf[AnyRef]))
        .sequence
        .map(ts => p.fromProduct(new ArrayProduct(ts.toArray.reverse)))
    }
  }
    def [E,A](es: List[Either[E,A]]) sequence: Either[E,List[A]] =
  traverse(es)(identity)

  def traverse[E,A,B](es: List[A])(f: A => Either[E, B]): Either[E, List[B]] =
    es.foldLeft[Either[E, List[B]]](Right(Nil))((tRes, h) => map2(f(h), tRes)(_ :: _))

  def map2[E, A, B, C](a: Either[E, A], b: Either[E, B])(f: (A, B) => C): Either[E, C] = 
    for { a1 <- a; b1 <- b } yield f(a1,b1)
}
