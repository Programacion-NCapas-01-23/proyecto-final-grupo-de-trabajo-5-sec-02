package com.code_of_duty.utracker_api.data.models

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "pensum")
data class Pensum(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id : UUID = UUID.randomUUID(),
    val plan: String,
    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "degree_fk", referencedColumnName = "id")
    val degree: Degree,
    @OneToMany(mappedBy = "pensum", fetch = FetchType.EAGER)
    val cycles: List<Cycle>? = null
)
