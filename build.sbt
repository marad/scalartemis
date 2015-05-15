organization := "io.github.marad"
name := "scalartmis"
version := "1.0.0"

scalaVersion := "2.11.6"

resolvers += Resolver.jcenterRepo

seq(bintraySettings:_*)
seq(bintrayPublishSettings:_*)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "org.scalacheck" %% "scalacheck" % "1.12.2" % "test",
  "org.mockito" % "mockito-core" % "1.10.19" % "test"
)

licenses += ("MIT", url("http://opensource.org/licenses/MIT"))
  
