
// Our Scala versions.
lazy val `scala-2.12`     = "2.12.14"
lazy val `scala-2.13`     = "2.13.6"
lazy val `scala-3.0`      = "3.0.1"

// Publishing
ThisBuild / organization := "org.tpolecat"
ThisBuild / licenses    ++= Seq(("MIT", url("http://opensource.org/licenses/MIT")))
ThisBuild / homepage     := Some(url("https://github.com/tpolecat/sourcepos"))
ThisBuild / developers   := List(
  Developer("tpolecat", "Rob Norris", "rob_norris@mac.com", url("http://www.tpolecat.org"))
)

// Compilation
ThisBuild / scalaVersion       := `scala-2.13`
ThisBuild / crossScalaVersions := Seq(`scala-2.12`, `scala-2.13`, `scala-3.0`)

lazy val root = project
  .in(file("."))
  .enablePlugins(NoPublishPlugin)
  .settings(
    Compile / unmanagedSourceDirectories := Seq.empty,
    Test / unmanagedSourceDirectories := Seq.empty,
  )
  .aggregate(sourcepos.jvm, sourcepos.js)

lazy val sourcepos = crossProject(JVMPlatform, JSPlatform)
  .crossType(CrossType.Pure)
  .in(file("."))
  .enablePlugins(AutomateHeaderPlugin)
  .settings(
    name         := "sourcepos",

    // Headers
    headerMappings := headerMappings.value + (HeaderFileType.scala -> HeaderCommentStyle.cppStyleLineComment),
    headerLicense  := Some(HeaderLicense.Custom(
      """|Copyright (c) 2020-2021 by Rob Norris
        |This software is licensed under the MIT License (MIT).
        |For more information see LICENSE or https://opensource.org/licenses/MIT
        |""".stripMargin
      )
    ),

    // Compilation
    Compile / doc     / scalacOptions --= Seq("-Xfatal-warnings"),
    Compile / doc     / scalacOptions ++= Seq(
      "-groups",
      "-sourcepath", (LocalRootProject / baseDirectory).value.getAbsolutePath,
      "-doc-source-url", "https://github.com/tpolecat/sourcepos/blob/v" + version.value + "€{FILE_PATH}.scala",
    ),

    // MUnit
    libraryDependencies += "org.scalameta" %%% "munit" % "0.7.28" % Test,
    testFrameworks += new TestFramework("munit.Framework"),

    // Scala 2 needs scala-reflect
    libraryDependencies ++= Seq("org.scala-lang" % "scala-reflect" % scalaVersion.value).filterNot(_ => scalaVersion.value.startsWith("3.")),

    // dottydoc really doesn't work at all right now
    Compile / doc / sources := {
      val old = (Compile / doc / sources).value
      if (scalaVersion.value.startsWith("3."))
        Seq()
      else
        old
    }
  )
