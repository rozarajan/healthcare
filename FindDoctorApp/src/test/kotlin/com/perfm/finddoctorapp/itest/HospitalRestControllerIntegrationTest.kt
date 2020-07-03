package com.perfm.finddoctorapp.itest

import com.perfm.finddoctorapp.TestHelper
import com.perfm.finddoctorapp.repository.HospitalDetailsRepository
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.nio.charset.Charset


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
class HospitalRestControllerIntegrationTest {

    private val URI = "/api/hospital"

    @Autowired private lateinit  var testHelper: TestHelper
    @Autowired private lateinit var hospitalDetailsRepository: HospitalDetailsRepository
    @Autowired private lateinit var mvc: MockMvc


    @Test
    fun givenHospitalId_whenGetHospitalDetailById_thenStatus200(){
        println(">> givenHospitalId_whenGetHospitalDetailById_thenStatus200")
        mvc.perform(get("/api/hospital/{id}","H1001")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.hospitalName").value("San Jose Medical Center"))
    }

    @Test
    fun whenGetHospitalDetails_thenStatus200(){
        println(">> whenGetHospitalDetails_thenStatus200")
        mvc.perform(get("/api/hospital/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content[0].hospitalName", `is`("Santa Clara Homestead Med Center")))
    }

    @Test
    fun givenHosptialDetails_whenSaveHospitalDetails_thenStatus200(){
        println(">> givenHosptialDetails_whenSaveHospitalDetails_thenStatus200")
        val hospitalDetailH1003 = testHelper.getHospitalDetailH1003()
        val APPLICATION_JSON_UTF8 = MediaType(MediaType.APPLICATION_JSON.type, MediaType.APPLICATION_JSON.subtype, Charset.forName("utf8"))

        mvc.perform(post("/api/hospital/add").contentType(APPLICATION_JSON_UTF8)
                        .content(testHelper.requestJson(hospitalDetailH1003))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.hospitalName").value("Redwood City Medical Center"))
    }
    @Test
    fun givenHosptialDetails_whenUpdateHospitalDetails_thenStatus200(){
        println(">> givenHosptialDetails_whenUpdateHospitalDetails_thenStatus200")
        val hospitalDetailH1002 = testHelper.getHospitalDetailH1002()
        hospitalDetailH1002.hospitalName = "Fremont MedicalCenter"
        val APPLICATION_JSON_UTF8 = MediaType(MediaType.APPLICATION_JSON.type, MediaType.APPLICATION_JSON.subtype, Charset.forName("utf8"))

        mvc.perform(put("/api/hospital/update").contentType(APPLICATION_JSON_UTF8)
                .content(testHelper.requestJson(hospitalDetailH1002))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.hospitalName").value("Fremont MedicalCenter"))
    }


    @AfterAll
    @Throws(Exception::class)
    public fun tearDown() {
        this.hospitalDetailsRepository.deleteById("H1003")
    }


}