package io.vangogiel.grpc.stream.example

case class Quote(
    origin: String,
    destination: String,
    departureTimeStampSeconds: Long,
    arrivalTimeStampSeconds: Long,
    transfers: Int,
    amount: BigDecimal
)
