package io.vangogiel.grpc.stream.example

import cats.effect.{ ExitCode, IO, IOApp }
import io.vangogiel.grpc.stream.example.GrpcServer.grpcServer

object Main extends IOApp {
  def run(args: List[String]): IO[ExitCode] = {
    grpcServer
      .evalMap(server => IO(server.start()))
      .useForever
      .map(grpc => ())
      .as(ExitCode.Success)
  }
}
