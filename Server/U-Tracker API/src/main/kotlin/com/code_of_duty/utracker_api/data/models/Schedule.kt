package com.code_of_duty.utracker_api.data.models

import jakarta.persistence.*

@Entity
@Table(name = "schedule")
data class Schedule(
    @Id
    val id: Int,
    @ManyToOne
    @JoinColumn(name = "subject_fk", referencedColumnName = "code")
    val subject: Subject,
    @ManyToOne
    @JoinColumn(name = "classTime_fk", referencedColumnName = "id")
    val classTime: ClassTime
)
