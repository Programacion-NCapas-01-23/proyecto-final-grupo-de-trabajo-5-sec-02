package com.code_of_duty.utracker_api.data.models

import jakarta.persistence.Column
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
    var username : String,
    val hashPassword : String,
    @Column(name = "image", nullable = true, columnDefinition = "varchar(255) default 'https://i.imgur.com/1qk9n3m.png'")
    var image : String? = null,
    @Column(name = "cum", nullable = false, columnDefinition = "float default 0.0")
    var cum : Float = 0.0f,
    @OneToOne()
    @JoinColumn(name = "degree_fk", referencedColumnName = "id", nullable = true)
    var degree : Degree? = null
)