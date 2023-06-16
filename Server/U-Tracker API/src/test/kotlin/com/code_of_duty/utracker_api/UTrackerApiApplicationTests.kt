package com.code_of_duty.utracker_api

import com.code_of_duty.utracker_api.data.dao.ClassTimeDao
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UTrackerApiApplicationTests {

    @Autowired
    lateinit var classTimeDao: ClassTimeDao

    @Test
    fun contextLoads() {
    }

    @Test
    fun testClassTimeDao(){
        println(classTimeDao.findAll())
    }
}
