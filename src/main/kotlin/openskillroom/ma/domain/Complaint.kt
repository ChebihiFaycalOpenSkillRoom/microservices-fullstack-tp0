package openskillroom.ma.domain

import java.time.Instant
import java.util.UUID

data class Complaint(
    val idempotencyKey: String,
    val id: ComplaintId = ComplaintId(UUID.randomUUID()),
    val customerId: String,
    val message: String,
    val createdAt: Instant = Instant.now()
)
