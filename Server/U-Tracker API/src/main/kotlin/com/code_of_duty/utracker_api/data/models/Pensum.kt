package com.code_of_duty.utracker_api.data.models

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "pensum")
data class Pensum(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id : UUID = UUID.randomUUID(),
    @OneToOne
    @JoinColumn(name = "degree_fk", referencedColumnName = "id")
    val degree: Degree
)