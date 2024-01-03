package io.vangogiel.grpc.stream.example

import cats.effect.kernel.GenConcurrent
import cats.effect.{ Concurrent, Spawn, Sync, Temporal }
import fs2.Stream
import io.vangogiel.grpc.stream.example.quote.{ QuoteRequest, QuoteResponse }

import scala.collection.mutable
import scala.concurrent.duration.*
import scala.collection.mutable.Queue
import scala.util.Random

object QuoteConsumerStream {
  def apply[F[_]: Temporal: Sync](
      queue: mutable.Queue[Quote]
  )(implicit F: GenConcurrent[F, _]): Stream[F, QuoteResponse] = {
    new QuoteConsumerStream[F](queue).createStream()
  }
}

class QuoteConsumerStream[F[_]: Temporal: Sync](queue: mutable.Queue[Quote])(implicit F: GenConcurrent[F, _]) {
  def createStream(): Stream[F, QuoteResponse] =
    Stream
      .awakeEvery[F](500.milliseconds)
      .evalMap(_ => Sync[F].delay(queue.dequeueFirst(_ => true)))
      .flatMap {
        case Some(quote) => Stream(mapToResponse(quote))
        case None        => Stream.empty
      }

  private def mapToResponse(quote: Quote): QuoteResponse = {
    QuoteResponse(
      quote.origin,
      quote.destination,
      quote.departureTimeStampSeconds,
      quote.arrivalTimeStampSeconds,
      quote.transfers,
      quote.amount.doubleValue
    )
  }
}
