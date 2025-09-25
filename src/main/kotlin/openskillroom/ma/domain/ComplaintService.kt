package openskillroom.ma.domain

import openskillroom.ma.infra.InMemoryComplaintRepositoryImpl
import org.springframework.stereotype.Service
import java.util.UUID

interface ComplaintService {
    fun create(customerId: String, message: String, key: String): Complaint
    fun get(id: UUID): Complaint?
    fun list(customerId: String?, page: Int, size: Int, sort: String): Pair<List<Complaint>, Long>
    fun findByIdempotencyKey(key: String): Complaint?
}

@Service
class ComplaintServiceImpl(private val repo: InMemoryComplaintRepositoryImpl) : ComplaintService {
    override fun create(customerId: String, message: String, key: String): Complaint {
        require(customerId.startsWith("CUST-")) { "Invalid customer id" }
        return repo.save(Complaint(idempotencyKey = key, customerId = customerId.trim(), message = message.trim()))
    }
    override fun get(id: UUID): Complaint? = repo.findById(id)
    override fun list(
        customerId: String?,
        page: Int,
        size: Int,
        sort: String
    ): Pair<List<Complaint>, Long> {
        return repo.findAll(customerId?.trim(), page, size, sort)
    }

    override fun findByIdempotencyKey(key: String): Complaint? {
        return repo.findAll(null, 0, Int.MAX_VALUE, "createdAt,desc").first.find { it.idempotencyKey == key }
    }

}
