package com.code_of_duty.utracker_api.data.models

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "schedule")
data class Schedule(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID = UUID.randomUUID(),
    val collection: Int,
    @ManyToOne
    @JoinColumn(name = "subject_fk", referencedColumnName = "code")
    val subject: Subject,
    @ManyToOne
    @JoinColumn(name = "classTime_fk", referencedColumnName = "id")
    val classTime: ClassTime
)
