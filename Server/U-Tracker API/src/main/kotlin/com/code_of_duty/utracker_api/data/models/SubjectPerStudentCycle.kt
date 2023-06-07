package com.code_of_duty.utracker_api.data.models

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "subject_x_stundentCycle")
data class SubjectPerStudentCycle(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID = UUID.randomUUID(),
    @ManyToOne
    @JoinColumn(name = "studentCycle_fk", referencedColumnName = "studentCycleId")
    val studentCycle: StudentCycle,
    @ManyToOne
    @JoinColumn(name = "subject_fk", referencedColumnName = "code")
    val subject: Subject
)
