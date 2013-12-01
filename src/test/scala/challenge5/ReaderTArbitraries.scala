package challenge5

import core._
import org.scalacheck.{Arbitrary, Gen}, Arbitrary._, Gen._

object ReaderTArbitraries {
  implicit def ReaderTArbitrary[F[_]: Monad, R, A: Arbitrary]: Arbitrary[ReaderT[F, R, A]] =
    Arbitrary(arbitrary[A] map (a => ReaderT.value[F, R, A](a)))
}
