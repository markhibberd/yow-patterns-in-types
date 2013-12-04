package warmup

import core._, Syntax._

object WarmupSpec extends test.Spec {
  import Warmup._
  "Warmup" should {
    "length should be the same as standard library" ! prop((xs: List[Int]) =>
      Warmup.length(xs) == xs.length)

    "lengthx should be the same as standard library" ! prop((xs: List[Int]) =>
      lengthX(xs) == xs.length)

    "length should be the same as lengthx" ! prop((xs: List[Int]) =>
      Warmup.length(xs) == lengthX(xs))

    "append should have have length equal to sum of length of both lists" ! prop((xs: List[Int], ys: List[Int]) =>
      Warmup.length(append(xs, ys)) == Warmup.length(xs) + Warmup.length(ys))

    "append should contain all elements from both lists" ! prop((xs: List[Int], ys: List[Int]) => {
      val zs = append(xs, ys)
      xs.forall(x => zs.contains(x)) && ys.forall(y => zs.contains(y))
    })

    "append should be associative" ! prop((xs: List[Int], ys: List[Int], zs: List[Int]) =>
      append(append(xs, ys), zs) == append(xs, append(ys, zs))
    )

    "append with Nil is identity" ! prop((xs: List[Int]) =>
      append(xs, Nil) == xs
    )

    "map should have length equal to input" ! prop((xs: List[Int], f: Int => Int) =>
      Warmup.length(Warmup.map(xs)(f)) == Warmup.length(xs))

    "map identity should be identity" ! prop((xs: List[Int]) =>
      Warmup.map(xs)(identity) == xs)

    "map(xs)(g compose f) is the same as map(map(xs)(f))(g)" ! prop((xs: List[Int], f: Int => Int, g: Int => Int) =>
      Warmup.map(xs)(g compose f) == Warmup.map(Warmup.map(xs)(f))(g))

    "filter length is less than or equal to input" ! prop((xs: List[Int], f: Int => Boolean) =>
      Warmup.length(filter(xs)(f)) <= Warmup.length(xs))

    "filter false is empty list" ! prop((xs: List[Int]) =>
      filter(xs)(_ => false) == Nil)

    "filter true is identity" ! prop((xs: List[Int]) =>
      filter(xs)(_ => true) == xs)

    "reverse maintains length" ! prop((xs: List[Int]) =>
      Warmup.length(reverse(xs)) == Warmup.length(xs))

   "reverse distributive law" ! prop((xs: List[Int], ys: List[Int]) =>
      reverse(append(xs, ys)) == append(reverse(ys), reverse(xs)))

    "reverse twice is identity" ! prop((xs: List[Int]) =>
      reverse(reverse(xs)) == xs)

    "ranges example" ! {
      ranges(List(1, 2, 3, 4, 7, 8, 9, 10, 30, 40, 41)) must_== List((1, 4), (7, 10), (30, 30), (40, 41))
    }
  }
}
