package openskillroom.ma.api

import openskillroom.ma.api.dto.ComplaintResponse
import openskillroom.ma.api.dto.CreateComplaintRequest
import openskillroom.ma.domain.Complaint

fun CreateComplaintRequest.toDomain() =
    Complaint(
        customerId = this.customerId,
        message = this.message
    )

fun Complaint.toResponse() =
    ComplaintResponse(
        id = this.id.value,
        customerId = this.customerId,
        message = this.message,
        createdAt = this.createdAt.toString()
    )
