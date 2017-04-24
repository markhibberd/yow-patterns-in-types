name := "introduction-to-fp-in-scala"

scalaVersion := "2.12.1"

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core"    % "7.2.11",
  "org.specs2" %% "specs2-core" % "3.8.6" % "test",
  "org.specs2" %% "specs2-scalacheck" % "3.8.6" % "test",
  "org.scalacheck" %% "scalacheck" % "1.13.4" % "test"
)

resolvers ++= Seq(
  "oss snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
  "oss releases"  at "http://oss.sonatype.org/content/repositories/releases"
)

scalacOptions := Seq(
  "-deprecation",
  "-unchecked",
  "-Xlint",
  "-feature",
  "-language:_"
)
