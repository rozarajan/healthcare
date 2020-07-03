package com.perfm.finddoctorapp

import com.perfm.finddoctorapp.service.DoctorService
import com.perfm.finddoctorapp.service.HospitalService
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UnitDoctorApplicationTest {

    private val URI = "/api/doctor"

    @Autowired
    private lateinit  var testHelper: TestHelper

    @Autowired
    private lateinit var wac: WebApplicationContext

    private lateinit var mockMvc: MockMvc

    @BeforeAll
    fun setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build()
    }

    @MockBean
    private lateinit var doctorService: DoctorService

    /*
    @Test
    fun testGetById() {
        println(">> testGetById")
        val hospitalDetailH1003: HospitalDetails = testHelper.getHospitalDetailH1003()
        var optionalHospitalDetails: Optional<HospitalDetails> = Optional.of(hospitalDetailH1003)

        given(hospitalService.getById(hospitalDetailH1003.id)).willReturn(optionalHospitalDetails)
        // Get Hospital Details
        this. mockMvc
                .perform(MockMvcRequestBuilders.get("/api/hospital/{id}","H1003")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.hospitalName").value("Redwood City Medical Center"))
    }
    *
    * */

    fun testGetById(){
        println(">> testGetById")

    }


}