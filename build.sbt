scalaVersion := "2.10.1"

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core" % "7.0.4",
  "org.scalaz" %% "scalaz-effect" % "7.0.4",
  "org.scalaz" %% "scalaz-typelevel" % "7.0.4",
  "org.scalaz" %% "scalaz-scalacheck-binding" % "7.0.4" % "test",
  "org.scalatest" %% "scalatest" % "1.9.1" % "test"
)

scalacOptions ++= Seq("-feature"
//  , "-Xlog-implicits"
)

initialCommands in console := "import scalaz._, Scalaz._"

name := "pure_simulation"
