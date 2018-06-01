lazy val app = project.in(file(".")).enablePlugins(PlayScala)
resolvers += "typesafe" at "http://repo.typesafe.com/typesafe/maven-releases/"

scalaVersion := "2.11.8"
