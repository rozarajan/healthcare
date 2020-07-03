package com.perfm.finddoctorapp.itest

import com.perfm.finddoctorapp.TestHelper
import com.perfm.finddoctorapp.model.HospitalDetails
import com.perfm.finddoctorapp.repository.HospitalDetailsRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HospitalTestRestTemplate {

    @LocalServerPort
    private var port = 0

    private val URI = "/api/hospital/"

    @Autowired
    private lateinit var rest: TestRestTemplate

    @Autowired private lateinit  var testHelper: TestHelper

    @Autowired private lateinit var hospitalDetailsRepository: HospitalDetailsRepository

    private var URL: String? = null

    @BeforeAll
    fun setup() {
        URL = "http://localhost:" + port + URI;
    }


    @DisplayName("GET /api/hospital{id} By ID")
    @Test
    fun getHospitalById(){
        //Given
        val hospitalDetailH1003: HospitalDetails = testHelper.getHospitalDetailH1003()
        val hospitalInsertedResult: HospitalDetails = hospitalDetailsRepository.insert(hospitalDetailH1003)
        //when
        /*
        val result: ResponseEntity<Person> = rest!!.getForEntity("$URL/person/$idInserted", Person::class.java)
        * */
        val result: ResponseEntity<HospitalDetails> =rest.getForEntity(URL+hospitalInsertedResult.id,HospitalDetails::class.java)
        //THEN
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(hospitalInsertedResult);
    }

    @DisplayName("POST /add 1 hospitaldetails")
    @Test
    fun testUpdate(){
        //Given
        val hospitalDetailH1004: HospitalDetails = testHelper.getHospitalDetailH1004()
        //when
        val responseEntity: ResponseEntity<HospitalDetails> = rest.postForEntity(URL+"add", hospitalDetailH1004, HospitalDetails::class.java )
        val result: HospitalDetails?= responseEntity.body
        //THEN
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.body).isEqualTo(hospitalDetailH1004);
    }


    @AfterAll
    @Throws(Exception::class)
    public fun tearDown() {
        hospitalDetailsRepository.deleteById("H1003")
        hospitalDetailsRepository.deleteById("H1004")
    }
}