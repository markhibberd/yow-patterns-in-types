package challenge7

import core._, Syntax._

object Challenge7Spec extends test.Spec {
  import Laws._
  import StateT._
  import StateTArbitraries._

  val unit: Unit = ()

  "StateT" should {
    "satisfy monad laws" ! monad.laws[StateT_[Id, Int]#l]

    "value should not modify state" ! prop((s: String, i: Int) =>
      StateT.value[Id, Int, String](s).run(i) === Id(i -> s))

    "return state for get" ! prop((i: Int) =>
      get[Id, Int].run(i) === Id(i -> i))

    "return view state for gets" ! prop((i: Int) =>
      gets[Id, Int, String](_.toString).run(i) === Id(i -> i.toString))

    "update state with modify" ! prop((i: Int, f: Int => Int) =>
      modify[Id, Int](f).run(i) == Id(f(i) -> unit))

    "clobber state with put"  ! prop((i: Int, j: Int) =>
      put[Id, Int](j).run(i) == Id(j -> unit))
  }

  implicit def StateTEqual[A: Equal]: Equal[StateT[Id, Int, A]] =
    Equal.from[StateT[Id, Int, A]]((a, b) =>
      a.run(0) === b.run(0))
}
