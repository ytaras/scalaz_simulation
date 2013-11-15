scalaVersion := "2.10.0"

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core" % "7.0.4",
  "org.scalaz" %% "scalaz-effect" % "7.0.4",
  "org.scalaz" %% "scalaz-typelevel" % "7.0.4",
  "org.scalaz" %% "scalaz-scalacheck-binding" % "7.0.4" % "test",
  "org.scalatest" %% "scalatest" % "2.0" % "test"
)

scalacOptions ++= Seq("-feature"
//  , "-Xlog-implicits"
)

initialCommands in console := "import scalaz._, Scalaz._"

name := "pure_simulation"
