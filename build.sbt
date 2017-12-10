name := "cgp_service"

version := "0.1"

scalaVersion := "2.12.4"

resolvers += Resolver.jcenterRepo

libraryDependencies ++= Seq(
  guice,
  "com.iheart"              %% "play-swagger"         % "0.7.3",
  "org.scalatestplus.play"  %% "scalatestplus-play"   % "3.1.2" % Test
)

lazy val root = (project in file(".")).enablePlugins(PlayScala)