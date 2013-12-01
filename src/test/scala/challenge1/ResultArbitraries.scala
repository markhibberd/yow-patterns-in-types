package challenge1

import org.scalacheck.{Arbitrary, Gen}, Arbitrary._, Gen._

object ResultArbitraries {
  import Result._

  implicit def ErrorArbitrary: Arbitrary[Error] =
    Arbitrary(oneOf(
      NotFound,
      InvalidRequest,
      InvalidMethod,
      Unauthorized,
      (arbitrary[Throwable] map Explosion)
    ))

  implicit def ResultArbitrary[A: Arbitrary]: Arbitrary[Result[A]] =
    Arbitrary(frequency(
      (2, arbitrary[Error] map fail),
      (8, arbitrary[A] map ok)
    ))
}
