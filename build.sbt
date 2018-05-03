import Dependencies._
import BuildSettings._
import com.lucidchart.sbt.scalafmt.ScalafmtCorePlugin.autoImport._

lazy val akkaDeps = Seq(akkaStream, akkaHttp, logback)

lazy val scalafmtSettings = Seq(
  scalafmtOnCompile in ThisBuild := true,
  scalafmtTestOnCompile in ThisBuild := true
)

lazy val dockerSettings = Seq(
  Docker / maintainer := "Juan Marin Otero",
  Docker / version := "latest",
  dockerBaseImage := "openjdk:10-jre-slim",
  dockerExposedPorts := Seq(9080),
  packageName := "hellows",
  dockerRepository := Some("jmarin")
)

lazy val packageSettings = Seq(
  // removes all jar mappings in universal and appends the fat jar
  mappings in Universal := {
    // universalMappings: Seq[(File,String)]
    val universalMappings = (mappings in Universal).value
    val fatJar = (assembly in Compile).value
    // removing means filtering
    val filtered = universalMappings filter {
      case (_, fileName) => !fileName.endsWith(".jar")
    }
    // add the fat jar
    filtered :+ (fatJar -> ("lib/" + fatJar.getName))
  },
  // the bash scripts classpath only needs the fat jar
  scriptClasspath := Seq((assemblyJarName in assembly).value),
  dependencyOverrides ++= akkaDeps
)

lazy val helloWS = (project in file("."))
  .enablePlugins(JavaServerAppPackaging, sbtdocker.DockerPlugin)
  .settings(defaultBuildSettings: _*)
  .settings(
    scalafmtSettings,
    dockerSettings,
    libraryDependencies ++= akkaDeps
  )
