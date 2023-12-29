package io.vangogiel.grpc.stream.example

import cats.effect.{ExitCode, IO, IOApp}
import com.comcast.ip4s.{host, port}
import io.vangogiel.grpc.stream.example.AppRoutes.restService
import io.vangogiel.grpc.stream.example.GrpcServer.grpcServer
import org.http4s.ember.server.EmberServerBuilder

object Main extends IOApp {
  def run(args: List[String]): IO[ExitCode] = {
    grpcServer
      .evalMap(server => IO(server.start()))
      .useForever
      .map(grpc => ())
      .as(ExitCode.Success)
  }
}
