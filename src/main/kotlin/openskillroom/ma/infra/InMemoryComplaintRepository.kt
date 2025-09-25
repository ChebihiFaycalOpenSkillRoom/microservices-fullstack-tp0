package openskillroom.ma.infra

import openskillroom.ma.domain.Complaint
import org.springframework.stereotype.Repository
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

interface InMemoryComplaintRepository {
    fun save(complaint: Complaint) : Complaint
    fun findById(id: UUID) : Complaint?
    fun findAll(customerId: String?, page: Int, size: Int, sort: String): Pair<List<Complaint>, Long>
}

@Repository
class InMemoryComplaintRepositoryImpl : InMemoryComplaintRepository {
    private val store = ConcurrentHashMap<UUID, Complaint>()

    override fun save(complaint: Complaint): Complaint {
        store[complaint.id.value] = complaint
        return complaint
    }

    override fun findById(id: UUID): Complaint? = store[id]
    override fun findAll(customerId: String?, page: Int, size: Int, sort: String): Pair<List<Complaint>, Long> {
        val filtered = customerId?.let {
            store.values.filter { it.customerId == customerId }
        } ?: store.values.toList()
        val sorted = when (sort) {
            "createdAt,desc" -> filtered.sortedByDescending { it.createdAt }
            "createdAt,asc" -> filtered.sortedBy { it.createdAt }
            else -> filtered.sortedByDescending { it.createdAt }
        }
        val fromIndex = page * size
        val toIndex = minOf(fromIndex + size, sorted.size)
        val paged = if (fromIndex >= sorted.size) {
            emptyList<Complaint>()
        } else {
            sorted.subList(fromIndex, toIndex)
        }
        return Pair(paged, filtered.size.toLong())
    }
}
