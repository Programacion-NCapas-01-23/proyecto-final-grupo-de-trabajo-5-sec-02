package com.code_of_duty.utracker_api.data.models

import jakarta.annotation.Nullable
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name="student")
data class Student(
    @Id
    val code : String,
    val username : String,
    val hashPassword : String,
    val image : String,
    val cum : Float,
    @OneToOne()
    @JoinColumn(name = "degree_fk", referencedColumnName = "id")
    val degree : Degree
)
