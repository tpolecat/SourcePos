ThisBuild / tlBaseVersion := "1.1"

// Our Scala versions.
lazy val `scala-2.12`     = "2.12.17"
lazy val `scala-2.13`     = "2.13.10"
lazy val `scala-3.0`      = "3.2.1"

// Publishing
ThisBuild / organization := "org.tpolecat"
ThisBuild / licenses    ++= Seq(("MIT", url("http://opensource.org/licenses/MIT")))
ThisBuild / homepage     := Some(url("https://github.com/tpolecat/sourcepos"))
ThisBuild / developers   := List(
  Developer("tpolecat", "Rob Norris", "rob_norris@mac.com", url("http://www.tpolecat.org"))
)
ThisBuild / tlSonatypeUseLegacyHost := false

// Compilation
ThisBuild / scalaVersion       := `scala-2.13`
ThisBuild / crossScalaVersions := Seq(`scala-2.12`, `scala-2.13`, `scala-3.0`)

lazy val root = tlCrossRootProject.aggregate(sourcepos)

lazy val sourcepos = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .crossType(CrossType.Pure)
  .in(file("sourcepos"))
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

    // MUnit
    libraryDependencies += "org.scalameta" %%% "munit" % "1.0.0-M7" % Test,

    // Scala 2 needs scala-reflect
    libraryDependencies ++= Seq("org.scala-lang" % "scala-reflect" % scalaVersion.value).filterNot(_ => tlIsScala3.value),

  )
  .jsSettings(
    tlVersionIntroduced := List("2.12", "2.13", "3").map(_ -> "1.0.1").toMap
  )
  .nativeSettings(
    tlVersionIntroduced := List("2.12", "2.13", "3").map(_ -> "1.1.0").toMap
  )
