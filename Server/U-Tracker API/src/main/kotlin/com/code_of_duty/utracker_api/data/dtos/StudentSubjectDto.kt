package com.code_of_duty.utracker_api.data.dtos

import java.math.BigDecimal


data class StudentSubjectDto (
    val code: String,
    val correlative: Int,
    val name: String,
    val uv: Int,
    val grade: BigDecimal?,
    val prerequisiteID: List<Int>?
)