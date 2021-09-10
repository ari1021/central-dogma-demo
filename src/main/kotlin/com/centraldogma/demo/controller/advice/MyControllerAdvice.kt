package com.centraldogma.demo.controller.advice

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class MyControllerAdvice {
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception, req: HttpServletRequest): Any {
        val httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
        return ResponseEntity.status(httpStatus).body(buildErrorMessage(httpStatus, e.message))
    }

    private fun buildErrorMessage(httpStatus: HttpStatus, message: String?): Map<String, String> {
        return mapOf(
                "message" to if (message.isNullOrBlank()) {
                    httpStatus.reasonPhrase
                } else {
                    message
                }
        )
    }
}