package com.code_of_duty.utracker_api.data.models

import com.code_of_duty.utracker_api.data.enums.CycleType
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "cycle")
data class Cycle(
    @Id
    val id : Int,
    val cycleType : CycleType,
    @ManyToOne
    @JoinColumn(name = "pensum_fk", referencedColumnName = "id")
    val pensum: Pensum
)
