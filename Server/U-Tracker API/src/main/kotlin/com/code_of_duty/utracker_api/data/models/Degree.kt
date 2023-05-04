package com.code_of_duty.utracker_api.data.models

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "degree")
data class Degree(
    @Id
    val id : Int,
    val name : String,
    @ManyToOne
    @JoinColumn(name = "faculty_fk", referencedColumnName = "id")
    val faculty : Faculty
)