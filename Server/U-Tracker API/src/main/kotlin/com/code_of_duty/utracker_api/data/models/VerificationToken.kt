package com.code_of_duty.utracker_api.data.models

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
data class VerificationToken(
    @Id
    val token : String,
    @OneToOne
    @JoinColumn(name = "student_fk", referencedColumnName = "code")
    val student : Student,
    val expiryDate : LocalDateTime
)
