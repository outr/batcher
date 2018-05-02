name := "batcher"
organization := "com.outr"
version := "1.0.0"
scalaVersion := "2.12.6"
scalacOptions ++= Seq("-unchecked", "-deprecation")
fork := true

publishTo in ThisBuild := sonatypePublishTo.value
sonatypeProfileName in ThisBuild := "com.outr"
publishMavenStyle in ThisBuild := true
licenses in ThisBuild := Seq("MIT" -> url("https://github.com/outr/batcher/blob/master/LICENSE"))
sonatypeProjectHosting in ThisBuild := Some(xerial.sbt.Sonatype.GitHubHosting("outr", "batcher", "matt@outr.com"))
homepage in ThisBuild := Some(url("https://github.com/outr/batcher"))
scmInfo in ThisBuild := Some(
  ScmInfo(
    url("https://github.com/outr/batcher"),
    "scm:git@github.com:outr/batcher.git"
  )
)
developers in ThisBuild := List(
  Developer(id="darkfrog", name="Matt Hicks", email="matt@matthicks.com", url=url("http://matthicks.com"))
)

libraryDependencies ++= Seq(
  "com.outr" %% "profig" % "2.2.1",
  "com.outr" %% "scribe-slf4j" % "2.3.3",
  "org.powerscala" %% "powerscala-io" % "2.0.5"
)