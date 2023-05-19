package com.code_of_duty.utracker_api.data.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "admins")
data class Admins(
    @Id
    val id: UUID,
    @Column(unique = true)
    val username: String,
    @Column(unique = true)
    val email: String,
    val hashPassword: String,
    val name: String,
)