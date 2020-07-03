package com.perfm.finddoctorapp.itest


import com.perfm.finddoctorapp.TestHelper
import com.perfm.finddoctorapp.model.HospitalDetails
import com.perfm.finddoctorapp.service.HospitalService
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.nio.charset.Charset
import java.util.*

/*
* Web Layer Tests
* */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
class HospitalControllerIntegrationTest {

    private val URI = "/api/hospital"

    @Autowired private lateinit var testHelper: TestHelper
    @Autowired private lateinit var wac: WebApplicationContext
    @Autowired private lateinit var mockMvc: MockMvc
    @MockBean private lateinit var hospitalService: HospitalService

    @BeforeAll
    fun setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build()
    }

    @Test
    fun testGetById() {
        println(">> testGetById")
        val hospitalDetailH1003: HospitalDetails = testHelper.getHospitalDetailH1003()
        var optionalHospitalDetails: Optional<HospitalDetails> = Optional.of(hospitalDetailH1003)

        given(hospitalService.getById(hospitalDetailH1003.id)).willReturn(optionalHospitalDetails)
        // Get Hospital Details
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/hospital/{id}", "H1003")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.hospitalName").value("Redwood City Medical Center"))
    }

    @Test
    fun testUpdate() {
        val APPLICATION_JSON_UTF8 = MediaType(MediaType.APPLICATION_JSON.type, MediaType.APPLICATION_JSON.subtype, Charset.forName("utf8"))
        println(">> testUpdateHospitalDetails")
        val hospitalDetailH1003 = testHelper.getHospitalDetailH1003()
        hospitalDetailH1003.hospitalName = "Redwood City MedicalCenter"
        given(hospitalService.update(hospitalDetailH1003)).willReturn(hospitalDetailH1003)
        // Get Hospital Details
        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/api/hospital/update").contentType(APPLICATION_JSON_UTF8)
                        .content(testHelper.requestJson(hospitalDetailH1003))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.hospitalName").value("Redwood City MedicalCenter"))
    }

    @Test
    fun testUpdate2() {
        val APPLICATION_JSON_UTF8 = MediaType(MediaType.APPLICATION_JSON.type, MediaType.APPLICATION_JSON.subtype, Charset.forName("utf8"))
        println(">> testUpdateHospitalDetails")
        val hospitalDetailH1003 = testHelper.getHospitalDetailH1004()
        given(hospitalService.update(hospitalDetailH1003)).willReturn(hospitalDetailH1003)
        // Get Hospital Details
        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/api/hospital/update").contentType(APPLICATION_JSON_UTF8)
                        .content(testHelper.requestJson(hospitalDetailH1003))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.hospitalName").value("Redwood City Medical Center"))}
    }

}