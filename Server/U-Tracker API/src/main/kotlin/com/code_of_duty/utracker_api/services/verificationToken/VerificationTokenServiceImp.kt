package com.code_of_duty.utracker_api.services.verificationToken

import com.code_of_duty.utracker_api.data.dao.StudentDao
import com.code_of_duty.utracker_api.data.dao.VerificationTokenDao
import com.code_of_duty.utracker_api.data.models.VerificationToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class VerificationTokenServiceImp(private val verificationTokenDao: VerificationTokenDao) : VerificationTokenService {

    @Autowired
    lateinit var studentDao: StudentDao
    override fun createVerificationToken(studentCode: String): VerificationToken{
        val character = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        lateinit var token: String

        do{
            token= (1..6)
                .map { character.random() }
                .joinToString("")
        }while(verificationTokenDao.existsByToken(token))

        val user = studentDao.findById(studentCode).orElse(null)

        val expDate = LocalDateTime.now().plusDays(1)

        val verificationToken = VerificationToken(token = token, student = user, expiryDate = expDate)

        return verificationTokenDao.save(verificationToken)
    }
    override fun deleteVerificationToken(token: String) {
        TODO("Not yet implemented")
    }
}