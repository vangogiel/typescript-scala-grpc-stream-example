import sbt.*

object Dependencies {
  val Versions = new {
    val Scala3 = "3.3.0"
    val Http4s = "0.23.23"
    val weaver = "0.8.3"
  }

  object Compile {
    val grpcNettyShaded = "io.grpc" % "grpc-netty-shaded" % scalapb.compiler.Version.grpcJavaVersion
    val http4s = "org.http4s" %% "http4s-ember-server" % Versions.Http4s
    val http4sDsl = "org.http4s" %% "http4s-dsl" % Versions.Http4s
    val https4sCirce = "org.http4s" %% "http4s-circe" % Versions.Http4s
  }

  object Test {
    val weaverCats = "com.disneystreaming" %% "weaver-cats" % Versions.weaver % "test"
  }
}
