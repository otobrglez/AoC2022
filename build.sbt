import Dependencies._

ThisBuild / scalaVersion     := "3.2.1"
ThisBuild / version          := "0.0.0"
ThisBuild / organization     := "com.pinkstack"
ThisBuild / organizationName := "pinkstack"

lazy val root = (project in file("."))
  .settings(
    name := "aoc",
    libraryDependencies ++= zio,
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )

scalacOptions ++= Seq(
  "-explain",
  "-encoding",
  "UTF-8",
  "-unchecked",
  "-deprecation",
  "-print-lines"
)

fork / run := true
