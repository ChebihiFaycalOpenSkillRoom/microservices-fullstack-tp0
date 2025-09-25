package openskillroom.ma.domain

import java.util.UUID

@JvmInline
value class ComplaintId(val value: UUID) {
    companion object {
        fun from(raw: String): ComplaintId = ComplaintId(UUID.fromString(raw))
    }
}
