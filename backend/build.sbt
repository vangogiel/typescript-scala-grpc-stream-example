import CiCommands.{ ciBuild, devBuild }

lazy val protobuf =
  project
    .in(file("protobuf"))
    .settings(
      name := "protobuf",
      scalaVersion := Dependencies.Versions.Scala3
    )
    .enablePlugins(Fs2Grpc)

lazy val root =
  project
    .in(file("app"))
    .settings(
      name := "app",
      scalaVersion := Dependencies.Versions.Scala3,
      libraryDependencies ++= Seq(
        Dependencies.Compile.grpcNettyShaded,
        Dependencies.Compile.http4s,
        Dependencies.Compile.http4sDsl,
        Dependencies.Compile.https4sCirce,
        Dependencies.Test.weaverCats
      ),
      testFrameworks += new TestFramework("weaver.framework.CatsEffect")
    )
    .dependsOn(protobuf)

commands ++= Seq(ciBuild, devBuild)

scalafmtOnCompile := true
scalafmtConfig := file(".scalafmt.conf")
