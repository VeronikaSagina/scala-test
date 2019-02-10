import Dependencies._

lazy val root = (project in file(".")).
  settings(
    version := "1.1",
    scalaVersion := "2.12.6",
    name := "dpd",

    libraryDependencies ++= commonDependencies,
    libraryDependencies ++= testDependencies,
    libraryDependencies ++= dbDependencies,
    libraryDependencies ++= akkaDependencies,
    libraryDependencies ++= loggingDependencies,


      // scapegoatVersion in ThisBuild := "1.3.8",

    logBuffered in Test := false,
    webappWebInfClasses := true,
    target in webappPrepare := target.value / "WebContent"

  ).enablePlugins(TomcatPlugin)

lazy val commonDependencies = Seq(
  "org.scala-lang.modules" %% "scala-xml" % "1.1.0",
  "com.typesafe" % "config" % "1.3.2",
  "io.spray" %% "spray-json" % "1.3.4",
  "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided"
)

lazy val dbDependencies = Seq(
  "org.playframework.anorm" %% "anorm" % "2.6.2",
  "org.postgresql" % "postgresql" % "42.2.5",
  "com.zaxxer" % "HikariCP" % "2.7.8"
)

lazy val akkaDependencies = Seq(
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.13"
)

val testDependencies = Seq(
  "org.scalactic" %% "scalactic" % "3.0.5",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test",
  "org.scalamock" %% "scalamock" % "4.1.0" % Test,
  "com.typesafe.akka" %% "akka-http-testkit" % "10.1.3" % Test
)

val loggingDependencies = Seq(
  "org.slf4j" % "slf4j-log4j12" % "1.7.25",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.7.1"
)