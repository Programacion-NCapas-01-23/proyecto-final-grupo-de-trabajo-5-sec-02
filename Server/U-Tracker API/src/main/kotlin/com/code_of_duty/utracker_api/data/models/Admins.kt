package com.code_of_duty.utracker_api.data.models

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "admins")
data class Admins(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID = UUID.randomUUID(),
    @Column(unique = true)
    val username: String,
    @Column(unique = true)
    val email: String,
    val hashPassword: String,
    val name: String,
)