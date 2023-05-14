package com.code_of_duty.utracker_api.utils

import com.code_of_duty.utracker_api.data.dtos.MessageDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(StudentNotFoundException::class)
    fun handleStudentNotFoundException(ex: StudentNotFoundException): ResponseEntity<MessageDto> {
        return ResponseEntity(MessageDto(message = ex.message.toString()), HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, String?>> {
        val errors: MutableMap<String, String?> = HashMap()
        ex.bindingResult.fieldErrors.forEach { error ->
            errors[error.field] = error.defaultMessage
        }
        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadable(ex: HttpMessageNotReadableException): ResponseEntity<MessageDto> {
        return ResponseEntity(MessageDto(message = ex.message.toString()), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(StudentCodeNotMatchException::class)
    fun handleStudentCodeNotMatchException(ex: StudentCodeNotMatchException): ResponseEntity<MessageDto> {
        return ResponseEntity(MessageDto(message = ex.message.toString()), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(UnauthorizedException::class)
    fun handleUnauthorizedException(ex: UnauthorizedException): ResponseEntity<MessageDto> {
        return ResponseEntity(MessageDto(message = ex.message.toString()), HttpStatus.UNAUTHORIZED)
    }
}