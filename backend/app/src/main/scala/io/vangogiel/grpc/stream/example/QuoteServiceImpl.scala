package io.vangogiel.grpc.stream.example

import cats.effect.Concurrent
import fs2.Stream
import io.grpc.Metadata
import io.vangogiel.grpc.stream.example.quote.{QuoteFs2Grpc, QuoteRequest, QuoteResponse}

class QuoteServiceImpl[F[_]: Concurrent, ContextShift] extends QuoteFs2Grpc[F, Metadata] {
  override def requestQuoteStream(request: QuoteRequest, ctx: Metadata): Stream[F, QuoteResponse] = {
    Stream(
      QuoteResponse(
        origin = request.origin,
        destination = request.destination,
        departureTimeStampSeconds = 1703090666,
        arrivalTimeStampSeconds = 1703091666,
        transfers = 3,
        amount = 300.99
      )
    )
  }
}
