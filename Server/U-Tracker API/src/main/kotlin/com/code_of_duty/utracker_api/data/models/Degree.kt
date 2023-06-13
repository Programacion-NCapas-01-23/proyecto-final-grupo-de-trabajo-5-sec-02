package com.code_of_duty.utracker_api.data.models

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "degree")
data class Degree(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id : UUID = UUID.randomUUID(),
    val name : String,
    @ManyToOne
    @JoinColumn(name = "faculty_fk", referencedColumnName = "id")
    val faculty : Faculty
)