val dottyVersion = "0.25.0-RC2"

inThisBuild(
      Seq(scalaVersion := dottyVersion,
      testFrameworks += new TestFramework("munit.Framework")
      )
)

lazy val root = project
  .in(file("."))
  .aggregate(`scala-csv`,`csv-padding`)

lazy val `scala-csv` = project
  .in(file("./scala-csv"))
  .settings(
      name := "scala-csv",
      version := "0.1.0",
      libraryDependencies += "org.scalameta" %% "munit" % "0.7.9" % Test,
  )

lazy val `csv-padding` = project
  .in(file("./csv-padding"))
  .settings(
      name := "csv-padding",
      version := "0.1.0",
      libraryDependencies += "org.scalameta" %% "munit" % "0.7.9" % Test,
  ).dependsOn(`scala-csv`)