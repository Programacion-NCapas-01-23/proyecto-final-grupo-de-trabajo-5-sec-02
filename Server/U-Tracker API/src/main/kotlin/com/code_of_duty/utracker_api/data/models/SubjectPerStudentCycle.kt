package com.code_of_duty.utracker_api.data.models

import com.code_of_duty.utracker_api.data.enums.SubjectStatus
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "subject_x_student_cycle")
data class SubjectPerStudentCycle(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID = UUID.randomUUID(),
    var status: SubjectStatus,
    var grade: Float,
    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "studentCycle_fk", referencedColumnName = "studentCycleId")
    val studentCycle: StudentCycle,
    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "subject_fk", referencedColumnName = "code")
    val subject: Subject
)
