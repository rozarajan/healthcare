package com.perfm.finddoctorapp

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FindDoctorAppApplicationTests {

    @LocalServerPort
    var randomServerPort = 0


    @Test
    fun testOne() {
        println(">> TODO")
        assertEquals("Redwood", "Redwood")
    }



}
