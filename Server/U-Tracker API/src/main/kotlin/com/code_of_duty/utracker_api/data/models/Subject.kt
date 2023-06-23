package com.code_of_duty.utracker_api.data.models

import jakarta.persistence.*

@Entity
@Table(name = "subject")
data class Subject(
    @Id
    val code: String,
    val name: String,
    val uv: Int,
    var estimateGrade: Int = 6,
    @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
    val assessments: MutableList<Assessment> = mutableListOf(),

    @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
    val subjectPerCycles: MutableList<SubjectPerCycle> = mutableListOf()
)
