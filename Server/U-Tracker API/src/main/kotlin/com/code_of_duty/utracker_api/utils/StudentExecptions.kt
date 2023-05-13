package com.code_of_duty.utracker_api.utils

import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class StudentNotFoundException(message: String?) : RuntimeException(message)