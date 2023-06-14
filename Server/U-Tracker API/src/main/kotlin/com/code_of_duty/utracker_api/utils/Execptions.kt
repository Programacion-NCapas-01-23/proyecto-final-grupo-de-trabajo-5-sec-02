package com.code_of_duty.utracker_api.utils

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class StudentNotFoundException(message: String?) : RuntimeException(message)

@ResponseStatus(HttpStatus.BAD_REQUEST)
class StudentCodeNotMatchException(message: String?) : RuntimeException(message)

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class UnauthorizedException(message: String?) : RuntimeException(message)

@ResponseStatus(HttpStatus.NOT_FOUND)
class ExceptionNotFound(message: String?) : RuntimeException(message)