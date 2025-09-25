package openskillroom.ma.domain

sealed class DomainError(val msg: String) : RuntimeException(msg) {
    data class InvalidCustomerId(val id: String) : DomainError("Invalid customer ID: $id")
    data class ComplaintNotFound(val id: String) : DomainError("Complaint not found with ID: $id")
}
