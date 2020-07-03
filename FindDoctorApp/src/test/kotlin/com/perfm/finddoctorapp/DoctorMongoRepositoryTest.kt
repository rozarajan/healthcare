package com.perfm.finddoctorapp

import com.perfm.finddoctorapp.model.Doctor
import com.perfm.finddoctorapp.model.HospitalDetails
import com.perfm.finddoctorapp.repository.DoctorRepository
import com.perfm.finddoctorapp.service.DoctorServiceImpl
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DoctorMongoRepositoryTest {

    @LocalServerPort
    private var port = 0
    @Autowired
    private lateinit var  doctorServiceImpl: DoctorServiceImpl
    @Autowired
    private lateinit var doctorRepository: DoctorRepository
    @Autowired
    private lateinit  var testHelper: TestHelper


    @BeforeAll
    fun setUp(){
        println(">> Setup")
        val doctor: Doctor = testHelper.getDoctorD1003()
        val result: Doctor = doctorRepository.insert(doctor)

        assertEquals("D1003", result.id)
        assertEquals("Joseph", result.lastName)
        assertNotNull(doctorRepository.findById("D1003"))
    }

    @Test
    fun testOne() {
        println(">> TODO")
        assertEquals("Redwood", "Redwood")
    }
    @Test
    fun testGetById(){
       val doctor =  doctorRepository.findById("D1003").get()
        assertNotNull(doctor)
        assertEquals("D1003", doctor.id)
        assertEquals("Edward", doctor.firstName)
        val doctors:Iterable<Doctor> = doctorRepository.findAll()
        var count = 0
        for (p in doctors) {
            count++
        }
        assertEquals(count, 3)
    }

    @Test
    fun testUpdate(){
        var doctor =  doctorRepository.findById("D1003").get()
        doctor.professionalStatement = "Newly Updated"
       var updatedDoctor =  doctorServiceImpl.update(doctor)
        assertNotNull(updatedDoctor)
        assertEquals("Newly Updated",updatedDoctor.professionalStatement)
        assertEquals("D1003", doctor.id)
        assertEquals("Edward", doctor.firstName)
    }

    @AfterAll
    @Throws(Exception::class)
    public fun tearDown() {
      this.doctorRepository.deleteById("D1003")
    }
}