package com.code_of_duty.utracker_api.data.models

import com.code_of_duty.utracker_api.data.enums.CycleType
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "cycle")
data class Cycle(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID = UUID.randomUUID(),
    val cycleType: CycleType,
    val name: String,
    @Column(name = "order_value")
    val orderValue: Int? = 1,
    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "pensum_fk", referencedColumnName = "id")
    val pensum: Pensum,
    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinTable(
        name = "subject_x_cycle",
        joinColumns = [JoinColumn(name = "cycle_fk", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "subject_fk", referencedColumnName = "code")]
    )
    val subjects: List<Subject> = emptyList()
)

