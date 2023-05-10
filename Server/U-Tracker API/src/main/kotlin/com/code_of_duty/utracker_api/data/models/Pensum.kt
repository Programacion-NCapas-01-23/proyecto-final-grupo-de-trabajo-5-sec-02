package com.code_of_duty.utracker_api.data.models

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "pensum")
data class Pensum(
    @Id
    val id : Int,
    @OneToOne
    @JoinColumn(name = "degree_fk", referencedColumnName = "id")
    val degree: Degree
)
