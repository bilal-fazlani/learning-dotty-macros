val dottyVersion = "0.25.0-RC2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "csv-parse",
    version := "0.1.0",

    scalaVersion := dottyVersion,

    libraryDependencies += "org.scalameta" %% "munit" % "0.7.9" % Test,
    
    testFrameworks += new TestFramework("munit.Framework")
  )
