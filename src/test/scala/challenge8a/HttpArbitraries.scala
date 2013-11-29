package challenge8a

import challenge0._
import org.scalacheck.{Arbitrary, Gen}, Arbitrary._, Gen._

object HttpArbitraries {
  def HeaderGen: Gen[(String, String)] =
    for {
      h <- Gen.alphaStr
      v <- Gen.alphaStr
    } yield h -> v

  def HeadersGen: Gen[Headers] =
    Gen.listOf(HeaderGen) map (hs => Headers(hs.toVector))

  def HttpReadGen: Gen[HttpRead] =
    for {
      method  <- arbitrary[Method]
      headers <- HeadersGen
      body    <- Gen.alphaStr
    } yield HttpRead(method, body, headers)

  def HttpStateGen: Gen[HttpState] =
    HeadersGen map (HttpState.apply)

  def HttpConstGen[A: Arbitrary]: Gen[Http[A]] =
    arbitrary[A] map (Http.value(_))

  def HttpRandGen[A: Arbitrary]: Gen[Http[A]] =
      for {
        log <- arbitrary[String]
        s   <- arbitrary[HttpState]
        a   <- arbitrary[A]
      } yield for {
        _ <- Http.log(log)
        _ <- Http.httpModify(_ => s)
      } yield a

  implicit def HttpReadArbitrary: Arbitrary[HttpRead] =
    Arbitrary(HttpReadGen)

  implicit def HttpStateArbitrary: Arbitrary[HttpState] =
    Arbitrary(HttpStateGen)

  implicit def MethodArbitrary: Arbitrary[Method] =
    Arbitrary(oneOf(Options, Get, Head, Post, Put, Delete, Trace, Connect))

  implicit def HttpArbitrary[A: Arbitrary]: Arbitrary[Http[A]] =
    Arbitrary(oneOf(HttpConstGen[A], HttpRandGen[A]))
}
