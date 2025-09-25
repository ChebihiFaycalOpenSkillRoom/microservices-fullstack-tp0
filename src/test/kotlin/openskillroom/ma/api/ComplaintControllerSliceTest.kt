package openskillroom.ma.api

import openskillroom.ma.domain.Complaint
import openskillroom.ma.domain.ComplaintId
import openskillroom.ma.domain.ComplaintService
import org.hamcrest.CoreMatchers.containsString

import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import java.util.UUID


@WebMvcTest(controllers = [ComplaintController::class])
class ComplaintControllerSliceTest(@Autowired val mvc: MockMvc) {
    @MockBean lateinit var complaintService: ComplaintService

    @Test
    fun `create and return 201 and location`() {
        val id = ComplaintId(UUID.randomUUID())
        given(complaintService.create("CUST-123", "This is a test complaint"))
            .willReturn(
                Complaint(
                    id = id,
                    customerId = "CUST-123",
                    message = "This is a test complaint"
                )
            )
        mvc.perform(post("/api/complaints")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""{"customerId":"CUST-123","message":"This is a test complaint"}"""))
            .andExpect(status().isCreated)
            .andExpect(header().string("Location", containsString("/api/complaints/")))
    }
}
