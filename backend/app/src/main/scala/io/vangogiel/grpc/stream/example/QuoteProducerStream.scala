package io.vangogiel.grpc.stream.example

import cats.Monad
import cats.effect.{ Async, Temporal }
import cats.effect.std.Queue
import cats.syntax.all.*
import fs2.Stream
import io.vangogiel.grpc.stream.example.quote.QuoteRequest

import scala.collection.mutable
import scala.concurrent.duration.*
import scala.util.Random

object QuoteProducerStream {
  def apply[F[_]: Async](queue: mutable.Queue[Quote], quoteRequest: QuoteRequest): Stream[F, Unit] = {
    new QuoteProducerStream[F](queue).createStream(quoteRequest)
  }
}

class QuoteProducerStream[F[_]: Async](queue: mutable.Queue[Quote]) {
  def createStream(quoteRequest: QuoteRequest): Stream[F, Unit] =
    Stream
      .repeatEval(Async[F].delay(generateRandomQuote(quoteRequest)))
      .evalTap(_ => Async[F].sleep(Random.between(0.5, 4).seconds))
      .take(Random.between(4, 10))
      .map(quote => queue.enqueue(quote))

  private def generateRandomQuote(quoteRequest: QuoteRequest): Quote = {
    val departureTimestamp = (System.currentTimeMillis() + Random.nextLong(100000000)) / 1000
    Quote(
      quoteRequest.origin,
      quoteRequest.destination,
      departureTimeStampSeconds = departureTimestamp,
      arrivalTimeStampSeconds = departureTimestamp + Random.nextLong(100000000),
      transfers = Random.between(0, 3),
      amount = BigDecimal(Random.between(200.00, 1500.00)).setScale(2, BigDecimal.RoundingMode.HALF_UP)
    )
  }
}
