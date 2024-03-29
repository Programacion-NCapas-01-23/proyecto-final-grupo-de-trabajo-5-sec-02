package com.code_of_duty.utracker_api.data.models

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name = "assessment")
data class Assessment(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID = UUID.randomUUID(),
    var name: String,
    var percentage: Int,
    @Column(columnDefinition = "DATE")
    @Temporal(TemporalType.DATE)
    var date: LocalDate,
    @Column(precision = 10, scale = 2)
    var grade: BigDecimal?,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_per_student_cycle_fk", referencedColumnName = "id")
    val subjectPerStudentCycle: SubjectPerStudentCycle
)
