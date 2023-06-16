package com.code_of_duty.utracker_api.services.api.verificationToken

import com.code_of_duty.utracker_api.data.dao.StudentDao
import com.code_of_duty.utracker_api.data.dao.VerificationTokenDao
import com.code_of_duty.utracker_api.data.models.VerificationToken
import com.code_of_duty.utracker_api.utils.ExceptionNotFound
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class VerificationTokenServiceImp(
    private val verificationTokenDao: VerificationTokenDao,
    @Value("\${TokenGenerator.characters}") private val characters: String
) : VerificationTokenService {

    @Autowired
    lateinit var studentDao: StudentDao
    override fun createVerificationToken(studentCode: String): VerificationToken{
        lateinit var token: String
        do{
            token= (1..6)
                .map { characters.random() }
                .joinToString("")
        }while(verificationTokenDao.existsByToken(token))

        val user = studentDao.findById(studentCode).orElse(null)

        verificationTokenDao.findByStudent(user)?.let { verificationTokenDao.delete(it) }

        val expDate = LocalDateTime.now().plusMinutes(15)

        val verificationToken = VerificationToken(token = token, student = user, expiryDate = expDate)

        return verificationTokenDao.save(verificationToken)
    }
    override fun deleteVerificationToken(token: String) {
        val verificationToken = verificationTokenDao.findById(token).orElseThrow {
            ExceptionNotFound("invalid token")
        }

        verificationTokenDao.delete(verificationToken)
    }

    override fun findByToken(token: String) = verificationTokenDao.findById(token).orElseThrow {
        ExceptionNotFound("invalid token")
    }
}