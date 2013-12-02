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

  "Example" should {
    "single" ! {
      Example.service("/single", "GET", "3") must_== Result.ok(3)
    }
    "double" ! {
      Example.service("/double", "GET", "5") must_== Result.ok(10)
    }
    "triple" ! {
      Example.service("/triple", "GET", "9") must_== Result.ok(27)
    }
    "handle invalid method" ! {
      Example.service("/triple", "INVALID", "9") must_== Result.fail(InvalidMethod)
    }
    "handle invalid path" ! {
      Example.service("/oops", "GET", "9") must_== Result.fail(NotFound)
    }
    "handle invalid request" ! {
      Example.service("/triple", "GET", "huh?") must_== Result.fail(InvalidRequest)
    }
    "handle unathorized method" ! {
      Example.service("/single", "POST", "8") must_== Result.fail(Unauthorized)
    }
    "default to 0 in failure" ! {
      Example.run("oops", "GET", "1") must_== 0
    }
    "not default to 0 in success" ! {
      Example.run("/double", "GET", "3") must_== 6
    }
  }
}
