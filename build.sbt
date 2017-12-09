
name := "cgp_service"

version := "0.1"

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  guice,
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
)

lazy val root = (project in file(".")).enablePlugins(PlayScala)