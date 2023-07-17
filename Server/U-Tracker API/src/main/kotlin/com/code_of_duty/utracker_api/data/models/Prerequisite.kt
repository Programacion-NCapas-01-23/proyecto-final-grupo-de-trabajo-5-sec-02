package com.code_of_duty.utracker_api.data.models

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Embeddable
data class PrerequisiteID(
    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "subject_fk", referencedColumnName = "code")
    val subjectCode : Subject,
    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "prerequisite_fk", referencedColumnName = "correlative")
    var prerequisiteCode : SubjectPerCycle
)

@Entity
@Table(name = "prerequisite")
data class Prerequisite(
    @EmbeddedId
    val prerequisite: PrerequisiteID
)