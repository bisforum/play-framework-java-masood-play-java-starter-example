name := """play-java-starter-example"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.4"

crossScalaVersions := Seq("2.11.12", "2.12.4")

libraryDependencies += guice

// Testing libraries for dealing with CompletionStage...
libraryDependencies += "org.assertj" % "assertj-core" % "3.6.2" % Test
libraryDependencies += "com.vmlens" % "concurrent-junit" % "1.0.2"
libraryDependencies += "org.awaitility" % "awaitility-scala" % "3.1.0"
libraryDependencies += ws
libraryDependencies += "org.mockito" % "mockito-core" % "2.10.0" % "test"

// Make verbose tests
testOptions in Test := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v"))

javaOptions in Test += "-Dconfig.file=conf/application.test.conf"

