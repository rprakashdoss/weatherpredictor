name := "WeatherPredict"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.8"

parallelExecution in Test := false

fork := true

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "org.apache.spark" % "spark-core_2.11" % "2.0.0",
  "org.jliszka" %% "probability-monad" % "1.0.1",
  "org.apache.spark" % "spark-mllib_2.11" % "2.0.0",
  "com.typesafe" % "config" % "1.3.1"

)

resolvers ++= Seq(
  "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
  "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/"
)
