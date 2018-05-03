import sbt.Keys._
import sbt._
import sbtassembly.AssemblyPlugin.autoImport._

object BuildSettings {
  val buildOrganization = "jmarin"
  val buildVersion      = "1.0.0"
  val buildScalaVersion = "2.12.4"

  val defaultBuildSettings = Defaults.coreDefaultSettings ++
    Seq(
      organization := buildOrganization,
      version      := buildVersion,
      scalaVersion := buildScalaVersion,
      scalacOptions ++= Seq(
        "-Xlint",
        "-deprecation",
        "-unchecked",
        "-feature"),
      aggregate in assembly := false,
      parallelExecution in Test := true
    )

}
