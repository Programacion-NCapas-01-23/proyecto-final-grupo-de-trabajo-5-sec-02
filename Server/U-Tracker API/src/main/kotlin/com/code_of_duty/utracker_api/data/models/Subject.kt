package com.code_of_duty.utracker_api.data.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

@Entity
@Table(name = "subject")
data class Subject(
    @Id
    val code : String,
    @Column(unique = true)
    val correlative : Int,
    val name : String,
    val uv : Int,
)
