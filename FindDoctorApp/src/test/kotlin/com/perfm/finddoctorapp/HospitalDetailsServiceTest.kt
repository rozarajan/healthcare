package com.perfm.finddoctorapp


import com.perfm.finddoctorapp.repository.HospitalDetailsRepository
import com.perfm.finddoctorapp.service.HospitalService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import java.util.*


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HospitalDetailsServiceTest {

    @Mock
    private lateinit var hospitalDetailsRepository: HospitalDetailsRepository

    @InjectMocks
    private lateinit var  hospitalService: HospitalService

    @Autowired
    private var testHelper: TestHelper? = null

    @BeforeAll
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testOne() {
        println(">> TODO")
        assertEquals("Redwood", "Redwood")
    }

    @Test
    fun testGetById() {
        val hospitalDetailH1003 = testHelper?.getHospitalDetailH1003()
        if (hospitalDetailH1003 != null) {
            `when`(hospitalDetailsRepository.findById(hospitalDetailH1003.id)).thenReturn(Optional.of(hospitalDetailH1003))
           val result = hospitalService.getById(hospitalDetailH1003.id)
            assertEquals("Redwood City Medical Center", result.get().hospitalName);
            assertEquals("H1003", result.get().id);
        }

    }
}