package com.code_of_duty.utracker_api.data.models

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import lombok.ToString


@Entity
@Table(name = "subject")
data class Subject(
    @Id
    val code: String,
    var name: String,
    var uv: Int,
    @OneToMany(mappedBy = "subject", fetch = FetchType.EAGER)
    @JsonIgnore
    val subjectPerCycles: MutableList<SubjectPerCycle> = mutableListOf()
) {
    override fun toString(): String {
        return "Subject(code='$code', name='$name', uv=$uv)"
    }
}
