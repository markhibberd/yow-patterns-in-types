package challenge0

object Syntax {
  implicit class MonadSyntax[M[_]: Monad, A](a: M[A]) {
    def map[B](f: A => B) =
      Monad[M].map(a)(f)

    def flatMap[B](f: A => M[B]) =
      Monad[M].bind(a)(f)
  }

  implicit class MonoidSyntax[A: Monoid](a: A) {
    def |+|(b: A) = Monoid[A].append(a, b)
  }

  implicit class EqualSyntax[A: Equal](value: A) {
    def ===(other: A) =
      Equal[A].equal(value, other)
  }
}
