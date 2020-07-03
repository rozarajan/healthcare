package com.perfm.finddoctorapp.itest

import com.perfm.finddoctorapp.TestHelper
import com.perfm.finddoctorapp.model.HospitalDetails
import com.perfm.finddoctorapp.repository.HospitalDetailsRepository
import com.perfm.finddoctorapp.service.HospitalService
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
class HospitalMongoRepositoryTest{

    @LocalServerPort
    private var port = 0
    @Autowired private lateinit var  hospitalService: HospitalService
    @Autowired private lateinit var hospitalDetailsRepository: HospitalDetailsRepository
    @Autowired private lateinit  var testHelper: TestHelper

    @BeforeAll
    fun setUp(){
        println(">> Setup")
        val hospitalDetailH1003: HospitalDetails = testHelper.getHospitalDetailH1003()
        val hospitalDetailH1004: HospitalDetails = testHelper.getHospitalDetailH1004()
        val result3: HospitalDetails = hospitalDetailsRepository.insert(hospitalDetailH1003)
        val result4: HospitalDetails = hospitalDetailsRepository.insert(hospitalDetailH1004)
        assertEquals("H1003", result3.id)
        assertEquals("Redwood City Medical Center", result3.hospitalName)
        assertNotNull(hospitalDetailsRepository.findById("H1003"))
        assertNotNull(hospitalDetailsRepository.findById("H1004"))
    }

    @Test
    fun testGetById() {
        val hospitalDetail: HospitalDetails = hospitalDetailsRepository.findById("H1003").get()
        assertNotNull(hospitalDetail)
        assertEquals("H1003", hospitalDetail.id)
        assertEquals("Redwood City Medical Center", hospitalDetail.hospitalName)
        val hospitalDetails: Iterable<HospitalDetails> = hospitalDetailsRepository.findAll()
        var count = 0
        for (p in hospitalDetails) {
            count++
        }
        assertEquals(count, 5)
    }

    @Test
    fun testUpdate(){
        var hospitalDetail: HospitalDetails = hospitalDetailsRepository.findById("H1004").get()
        hospitalDetail.hospitalName = "Indian Hill Medical Office"
        hospitalService.update(hospitalDetail)
        var hospitalDetailResult: HospitalDetails = hospitalDetailsRepository.findById("H1004").get()
        assertNotNull(hospitalDetailResult)
        assertEquals("Indian Hill Medical Office",hospitalDetailResult.hospitalName)
    }


    @AfterAll
    @Throws(Exception::class)
    public fun tearDown() {
        this.hospitalDetailsRepository.deleteById("H1003")
        this.hospitalDetailsRepository.deleteById("H1004")
    }


}