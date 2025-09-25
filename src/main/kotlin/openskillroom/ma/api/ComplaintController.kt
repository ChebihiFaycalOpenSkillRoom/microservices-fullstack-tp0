package openskillroom.ma.api

import openskillroom.ma.api.dto.ComplaintResponse
import openskillroom.ma.api.dto.CreateComplaintRequest
import openskillroom.ma.domain.ComplaintService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.UUID

@RestController
@RequestMapping("/api/complaints")
class ComplaintController(private val complaintService: ComplaintService) {

    @PostMapping
    fun create(
        @RequestHeader(name = "Idempotency-key", required = false) idem: String?,
        @RequestBody @Validated body: CreateComplaintRequest) : ResponseEntity<ComplaintResponse> {
        val key = idem ?: UUID.randomUUID().toString()
        val existing = complaintService.findByIdempotencyKey(key)
        val saved = existing ?: complaintService.create(body.customerId, body.message, key)
        val loc = URI.create("/api/complaints/${saved.id.value}")
        return ResponseEntity.created(loc).body(
            ComplaintResponse(
                id = saved.id.value,
                customerId = saved.customerId,
                message = saved.message,
                createdAt = saved.createdAt.toString()
            )
        )
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: UUID) : ResponseEntity<ComplaintResponse> =
        complaintService.get(id)?.let {
            ResponseEntity.ok(
                ComplaintResponse(
                    id = it.id.value,
                    customerId = it.customerId,
                    message = it.message,
                    createdAt = it.createdAt.toString()
                )
            )
        }?: ResponseEntity.notFound().build()

    @GetMapping
    fun list(
        @RequestParam(required = false) customerId: String?,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int,
        @RequestParam(defaultValue = "createdAt,desc") sort: String,
    ): PagedResponse<ComplaintResponse> {
        val (items, total) = complaintService.list(customerId, page, size, sort)
        return PagedResponse(items.map{it.toResponse()}, page, size, total)
    }

    data class PagedResponse<T>(val items: List<T>, val page: Int, val size: Int, val total: Long)

}
