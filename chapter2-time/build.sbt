lazy val app = project.in(file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  guice,
  "joda-time" % "joda-time" % "2.0",
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.0" % "test"
)
