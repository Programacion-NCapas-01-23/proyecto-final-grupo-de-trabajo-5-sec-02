package com.code_of_duty.utracker_api.data.models

import jakarta.persistence.Embeddable
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Embeddable
data class PrerequisiteID(
    @ManyToOne
    @JoinColumn(name = "subject_fk", referencedColumnName = "code")
    val subjectCode : Subject,
    @ManyToOne
    @JoinColumn(name = "prerequisite_fk", referencedColumnName = "correlative")
    val prerequisiteCode : SubjectPerCycle
)

@Entity
@Table(name = "prerequisite")
data class Prerequisite(
    @EmbeddedId
    val prerequisite: PrerequisiteID
)