package com.code_of_duty.utracker_api.data.dao

import com.code_of_duty.utracker_api.data.models.Student

interface LoginDao {
    fun findByCode(code: String?): Student?
}