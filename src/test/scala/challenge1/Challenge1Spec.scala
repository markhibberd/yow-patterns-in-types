package challenge1

import core._, Syntax._

object Challenge1Spec extends test.Spec {
  import Laws._
  import ResultArbitraries._

  "Equal" should {
    "satisfy equal laws" ! equal.laws[Error]
  }

  "Result" should {
    "satisfy equal laws with Int" ! equal.laws[Result[Int]]

    "satisfy equal laws with String" ! equal.laws[Result[String]]

    "satisfy monad laws" ! monad.laws[Result]

    "Ok#getOrElse always returns value" ! prop((i: Int, j: Int) =>
      Ok(i).getOrElse(j) == i)

    "Fail#getOrElse always returns value" ! prop((e: Error, j: Int) =>
      Fail[Int](e).getOrElse(j) == j)

    "Ok ||| x always returns value" ! prop((i: Int, r: Result[Int]) =>
      (Ok(i) ||| r) == Ok(i))

    "Fail ||| x always returns alternative" ! prop((e: Error, r: Result[Int]) =>
      (Fail[Int](e) ||| r) == r)

    "fold with fail" ! prop((e: Error) =>
      Result.fail[Int](e).fold(_ === e, _ => false))

    "fold with ok" ! prop((i: Int) =>
      Result.ok[Int](i).fold(_ => false, _ === i))
  }
}
