package openskillroom.ma.infra

import openskillroom.ma.domain.DomainError
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    data class Problem(
        val type: String,
        val title: String,
        val status: Int,
        val detail: String? = null,
        val instance: String? = null
    )


    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun onValidation(ex: MethodArgumentNotValidException): ResponseEntity<Problem> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            Problem(
                type = "https://example.com/probs/api/complaints",
                title = "Validation Error",
                status = HttpStatus.BAD_REQUEST.value(),
                detail = ex.bindingResult.allErrors.joinToString { it.defaultMessage ?: "Invalid value" }
            )
        )

    @ExceptionHandler(DomainError.InvalidCustomerId::class)
    fun invalidCustomerId(ex: DomainError.InvalidCustomerId): ResponseEntity<GlobalExceptionHandler.Problem> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            GlobalExceptionHandler.Problem(
                type = "https://example.com/probs/api/complaints",
                title = "Invalid Customer ID",
                status = HttpStatus.BAD_REQUEST.value(),
                detail = ex.msg
            )
        )


    @ExceptionHandler(DomainError.ComplaintNotFound::class)
    fun complaintNotFound(ex: DomainError.ComplaintNotFound): ResponseEntity<GlobalExceptionHandler.Problem> =
        ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            GlobalExceptionHandler.Problem(
                type = "https://example.com/probs/api/complaints",
                title = "Complaint Not Found",
                status = HttpStatus.NOT_FOUND.value(),
                detail = ex.msg
            )
        )
}
