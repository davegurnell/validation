import sbt.Keys._

lazy val root = project.in(file(".")).
  aggregate(fooJS, fooJVM)

lazy val validation = crossProject.in(file(".")).
  settings(
    scalaVersion := "2.11.0",
    scalacOptions ++= Seq("-deprecation", "-feature"),
    organization := "io.underscore",
    name := "validation",
    version := "0.0.1",
    publishTo := Some(Resolver.file("file", new File("../maven-repo"))),
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-reflect" % scalaVersion.value,
      "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.1",
      "org.specs2" %% "specs2" % "2.3.12" % "test"
    )
  )
  .jvmSettings(
    // Add JVM-specific settings here
  )
  .jsSettings(
    // Add JS-specific settings here
  )

lazy val fooJVM = validation.jvm
lazy val fooJS = validation.js



