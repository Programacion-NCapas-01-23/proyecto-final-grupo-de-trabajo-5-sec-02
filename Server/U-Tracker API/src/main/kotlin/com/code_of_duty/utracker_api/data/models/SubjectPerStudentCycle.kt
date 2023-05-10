package com.code_of_duty.utracker_api.data.models

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "subject_x_stundentCycle")
data class SubjectPerStudentCycle(
    @Id
    val id: Int,
    @ManyToOne
    @JoinColumn(name = "studentCycle_fk", referencedColumnName = "studentCycleId")
    val studentCycle: StudentCycle,
    @ManyToOne
    @JoinColumn(name = "subject_fk", referencedColumnName = "code")
    val subject: Subject
)
