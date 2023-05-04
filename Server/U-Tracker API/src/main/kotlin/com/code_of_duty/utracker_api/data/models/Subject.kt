package com.code_of_duty.utracker_api.data.models

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "subject")
data class Subject(
    @Id
    val code : String,
    val correlative : Int,
    val name : String,
    val uv : Int,
    //TODO (): add Foreign key when add more models
)
