package openskillroom.ma.api.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateComplaintRequest (
    @field:NotBlank(message = "Customer ID is required")
    val customerId: String,
    @field:NotBlank(message = "message is required")
    @field:Size(max = 2000, message = "message must not exceed 2000 characters")
    val message: String
)
