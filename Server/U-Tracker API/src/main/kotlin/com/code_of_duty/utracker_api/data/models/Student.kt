package com.code_of_duty.utracker_api.data.models

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name="student")
data class Student(
    @Id
    val code: String,
    @Column(unique = true)
    val username: String,
    @Column(unique = true)
    val email: String,
    val hashPassword: String,
    @Column(name = "image", nullable = true, columnDefinition = "varchar(255) default 'https://i.imgur.com/1qk9n3m.png'")
    var image: String? = null,
    @Column(name = "cum", nullable = false, columnDefinition = "float default 0.0")
    var cum: BigDecimal = 0.0.toBigDecimal(),
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "degree_fk", referencedColumnName = "id", nullable = true)
    var degree: Degree? = null,

    @OneToMany(mappedBy = "student", fetch = FetchType.EAGER)
    @JsonIgnore
    val studentCycles: List<StudentCycle>? = null
){
    @get:Transient
    val faculty: Faculty?
        get() = degree?.faculty
}