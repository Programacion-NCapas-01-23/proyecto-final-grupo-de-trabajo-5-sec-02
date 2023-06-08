package com.code_of_duty.utracker_api.data.models

import com.code_of_duty.utracker_api.data.enums.CycleType
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "student_cycle")
data class StudentCycle(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val studentCycleId: UUID = UUID.randomUUID(),
    val cycleType: CycleType,
    val year: Int,
    @ManyToOne
    @JoinColumn(name = "student_fk", referencedColumnName = "code")
    val student: Student,

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinTable(
        name = "subject_x_student_cycle",
        joinColumns = [JoinColumn(name = "studentCycle_fk", referencedColumnName = "studentCycleId")],
        inverseJoinColumns = [JoinColumn(name = "subject_fk", referencedColumnName = "code")]
    )
    val subjects: List<Subject>
)