package com.code_of_duty.utracker_api.services.verificationToken

import com.code_of_duty.utracker_api.data.models.VerificationToken
import org.springframework.stereotype.Service

@Service
interface VerificationTokenService {
    fun createVerificationToken(studentCode: String): VerificationToken
    fun deleteVerificationToken(token: String)

}