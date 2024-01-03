package io.vangogiel.grpc.stream.example

import cats.effect.*
import cats.effect.instances.all.parallelForGenSpawn
import cats.effect.std.Console
import cats.effect.unsafe.implicits.global
import cats.syntax.all.*
import cats.{ NonEmptyParallel, Parallel, ~> }
import fs2.Stream
import fs2.concurrent.SignallingRef
import io.grpc.Metadata
import io.vangogiel.grpc.stream.example.quote.{ QuoteFs2Grpc, QuoteRequest, QuoteResponse }

import scala.collection.mutable
import scala.concurrent.duration.*
import scala.collection.mutable.Queue
import scala.util.Random

class QuoteServiceImpl[F[_]: Temporal: Async] extends QuoteFs2Grpc[F, Metadata] {
  override def requestQuoteStream(request: QuoteRequest, ctx: Metadata): Stream[F, QuoteResponse] = {
    val queue = mutable.Queue[Quote]()
    val producer = QuoteProducerStream[F](queue, request)
    val consumer = QuoteConsumerStream[F](queue)
    consumer.concurrently(producer)
  }
}
