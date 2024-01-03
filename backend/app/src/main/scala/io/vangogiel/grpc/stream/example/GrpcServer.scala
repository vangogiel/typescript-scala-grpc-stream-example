package io.vangogiel.grpc.stream.example

import cats.effect.IO
import cats.effect.kernel.Resource
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder
import fs2.grpc.syntax.all.*
import io.grpc.{ Server, ServerServiceDefinition }
import io.vangogiel.grpc.stream.example.quote.QuoteFs2Grpc

object GrpcServer {
  private val quoteService: Resource[IO, ServerServiceDefinition] =
    QuoteFs2Grpc.bindServiceResource[IO](new QuoteServiceImpl())

  private def runServer(service: ServerServiceDefinition): Resource[IO, Server] =
    NettyServerBuilder
      .forPort(9999)
      .addService(service)
      .resource[IO]

  val grpcServer: Resource[IO, Server] = quoteService.flatMap(x => runServer(x))
}
