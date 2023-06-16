package com.code_of_duty.utracker_api.data.enums

enum class Days {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY;

    companion object {
        fun fromInt(type: Int) = values().first { it.ordinal == type }
    }
}