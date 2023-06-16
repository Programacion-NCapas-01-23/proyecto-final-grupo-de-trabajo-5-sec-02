package com.code_of_duty.utracker_api.data.enums

enum class SubjectStatus {
    APPROVED,
    IN_PROGRESS,
    REJECTED;

    companion object {
        fun fromInt(type: Int) = values().first { it.ordinal == type }
    }
}