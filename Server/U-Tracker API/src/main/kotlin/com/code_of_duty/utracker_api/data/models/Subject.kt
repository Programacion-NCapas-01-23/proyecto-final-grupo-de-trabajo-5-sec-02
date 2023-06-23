package com.code_of_duty.utracker_api.data.models

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "subject")
data class Subject(
    @Id
    val code: String,
    val name: String,
    val uv: Int,
    var estimateGrade: Int = 6,
    @OneToMany(mappedBy = "subject")
    val assessments: MutableList<Assessment> = mutableListOf(),

    @OneToMany(mappedBy = "subject")
    val subjectPerCycles: MutableList<SubjectPerCycle> = mutableListOf()
)
