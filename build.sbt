
// Our Scala versions.
lazy val `scala-2.12`     = "2.12.12"
lazy val `scala-2.13`     = "2.13.5"
lazy val `scala-3.0`      = "3.0.0"

lazy val sourcepos = project.in(file("."))
  .aggregate(core.jvm, core.js)
  .enablePlugins(NoPublishPlugin)

lazy val core = crossProject(JVMPlatform, JSPlatform)
  .crossType(CrossType.Pure)
  .in(file("core"))
  .settings(
    name         := "sourcepos",
    organization := "org.tpolecat",
    licenses    ++= Seq(("MIT", url("http://opensource.org/licenses/MIT"))),
    homepage     := Some(url("https://github.com/tpolecat/sourcepos")),
    developers   := List(
      Developer("tpolecat", "Rob Norris", "rob_norris@mac.com", url("http://www.tpolecat.org"))
    ),

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
    scalaVersion       := `scala-2.13`,
    crossScalaVersions := Seq(`scala-2.12`, `scala-2.13`, `scala-3.0`),
    Compile / doc     / scalacOptions --= Seq("-Xfatal-warnings"),
    Compile / doc     / scalacOptions ++= Seq(
      "-groups",
      "-sourcepath", (LocalRootProject / baseDirectory).value.getAbsolutePath,
      "-doc-source-url", "https://github.com/tpolecat/sourcepos/blob/v" + version.value + "€{FILE_PATH}.scala",
    ),

    // MUnit
    Compile / unmanagedSourceDirectories ++= {
      val major = if (scalaVersion.value.startsWith("3")) "-3" else "-2"
      List(CrossType.Pure, CrossType.Full).flatMap(
        _.sharedSrcDir(baseDirectory.value, "main").toList.map(f => file(f.getPath + major))
      )
    },
    Test / unmanagedSourceDirectories ++= {
      val major = if (scalaVersion.value.startsWith("3")) "-3" else "-2"
      List(CrossType.Pure, CrossType.Full).flatMap(
        _.sharedSrcDir(baseDirectory.value, "test").toList.map(f => file(f.getPath + major))
      )
    },
    libraryDependencies += ("org.scalameta" %%% "munit" % "0.7.27" % Test),

    testFrameworks += new TestFramework("munit.Framework"),

    // dottydoc really doesn't work at all right now
    Compile / doc / sources := {
      val old = (Compile / doc / sources).value
      if (scalaVersion.value.startsWith("3."))
        Seq()
      else
        old
    },
  )
  .settings(
    libraryDependencies ++= {
      if (scalaVersion.value.startsWith("3")) Nil else List("org.scala-lang" % "scala-reflect" % scalaVersion.value % "provided")
    }
  )
  .jsSettings(
    scalaJSLinkerConfig ~= (_.withModuleKind(ModuleKind.CommonJSModule))
  )
  .enablePlugins(AutomateHeaderPlugin)

