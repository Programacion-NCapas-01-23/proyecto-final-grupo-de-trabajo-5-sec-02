package com.code_of_duty.utracker_api.data.models

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "cycle")
data class Cycle(
    @Id
    val id : Int,
    val cycleNumber: Int, //TODO (): change to Enum
    val year: String
)
