package openskillroom.ma.api.dto

import java.util.UUID

data class ComplaintResponse(
    val id: UUID,
    val customerId: String,
    val message: String,
    val createdAt: String
)
