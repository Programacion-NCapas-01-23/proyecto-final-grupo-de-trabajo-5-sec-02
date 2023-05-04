package com.code_of_duty.utracker_api.data.models

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "faculty")
data class Faculty(
    @Id
    val id : Int,
    val name : String
)
