scalaVersion := "2.10.1"

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core" % "7.0.4",
  "org.scalaz" %% "scalaz-effect" % "7.0.4",
  "org.scalaz" %% "scalaz-typelevel" % "7.0.4",
  "org.scalaz" %% "scalaz-scalacheck-binding" % "7.0.4" % "test"
)

scalacOptions += "-feature"

initialCommands in console := "import scalaz._, Scalaz._"

name := "pure_simulation"
