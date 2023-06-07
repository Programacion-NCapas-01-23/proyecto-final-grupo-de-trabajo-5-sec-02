package com.code_of_duty.utracker_api.data.models

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "subject_x_cycle")
data class SubjectPerCycle(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id : UUID = UUID.randomUUID(),
    @ManyToOne
    @JoinColumn(name = "cycle_fk", referencedColumnName = "id")
    val cycle: Cycle,
    @ManyToOne
    @JoinColumn(name = "subject_fk", referencedColumnName = "code")
    val subject: Subject
)
