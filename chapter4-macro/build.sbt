lazy val app = project.in(file("."))

scalaVersion := "2.11.8"

resolvers += "typesafe" at "http://repo.typesafe.com/typesafe/maven-releases/"

libraryDependencies ++= Seq(
  json,
  "org.scalatestplus" %% "play" % "1.2.0" % "test"
)
