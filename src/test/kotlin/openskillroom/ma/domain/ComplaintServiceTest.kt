package openskillroom.ma.domain
import openskillroom.ma.infra.InMemoryComplaintRepositoryImpl
import kotlin.test.Test

class ComplaintServiceTest {
    private val repo = InMemoryComplaintRepositoryImpl()
    private val service = ComplaintServiceImpl(repo)

    @Test fun `create and save complaint` () {
        val complaint = service.create("CUST-123", "This is a test complaint")
        assert(complaint.id.toString().isNotEmpty())
        assert(complaint.customerId == "CUST-123")
        assert(complaint.message == "This is a test complaint")
    }
}
