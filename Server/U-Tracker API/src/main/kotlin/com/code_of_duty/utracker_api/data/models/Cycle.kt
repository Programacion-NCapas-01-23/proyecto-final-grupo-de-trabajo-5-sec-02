package com.code_of_duty.utracker_api.data.models

import com.code_of_duty.utracker_api.data.enums.CycleType
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "cycle")
data class Cycle(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id : UUID = UUID.randomUUID(),
    val cycleType : CycleType,
    @ManyToOne
    @JoinColumn(name = "pensum_fk", referencedColumnName = "id")
    val pensum: Pensum
)
