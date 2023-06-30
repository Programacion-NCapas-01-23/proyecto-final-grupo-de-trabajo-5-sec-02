package com.code_of_duty.utracker_api.data.models

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import lombok.ToString
import java.util.UUID

@Entity
@Table(name = "subject_x_cycle")
data class SubjectPerCycle(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id : UUID = UUID.randomUUID(),
    @Column(unique = true)
    val correlative : Int,
    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "cycle_fk", referencedColumnName = "id")
    val cycle: Cycle,
    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "subject_fk", referencedColumnName = "code")
    val subject: Subject
){
    override fun toString(): String {
        return "SubjectPerCycle(id=$id, correlative=$correlative, subject=$subject)"
    }
}
