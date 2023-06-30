package com.code_of_duty.utracker_api.data.models

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "degree")
data class Degree(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID = UUID.randomUUID(),
    val name: String,
    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "faculty_fk", referencedColumnName = "id")
    val faculty: Faculty?,
    @OneToMany(mappedBy = "degree", fetch = FetchType.EAGER)
    val pensums: List<Pensum>? = null
)