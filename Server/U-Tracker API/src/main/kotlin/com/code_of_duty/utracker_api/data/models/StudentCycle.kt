package com.code_of_duty.utracker_api.data.models

import com.code_of_duty.utracker_api.data.enums.CycleType
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "student_cycle")
data class StudentCycle(
    @Id
    val studentCycleId: Int,
    val cycleType: CycleType,
    val year: Int,
    @ManyToOne
    @JoinColumn(name = "student_fk", referencedColumnName = "code")
    val student: Student
)