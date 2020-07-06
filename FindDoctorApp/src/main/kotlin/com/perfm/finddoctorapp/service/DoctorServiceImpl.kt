package com.perfm.finddoctorapp.service

import com.perfm.finddoctorapp.model.Doctor
import com.perfm.finddoctorapp.model.Response
import com.perfm.finddoctorapp.repository.DoctorRepository
import com.perfm.finddoctorapp.repository.HospitalDetailsRepository
import com.perfm.finddoctorapp.util.BasicCrud
import org.kie.api.runtime.KieContainer
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class DoctorServiceImpl(@Autowired val kieContainer: KieContainer, val doctorRepository: DoctorRepository, val hospitalDetailsRepository: HospitalDetailsRepository):BasicCrud<String, Doctor> {
    private val log = LoggerFactory.getLogger(DoctorServiceImpl::class.java)
    override fun getAll(pageable: Pageable): Page<Doctor> {
        log.debug("Inside DoctorServiceImpl::getAll()")
        return doctorRepository.findAll(pageable)
    }
    override fun getById(id: String): Optional<Doctor> {
        log.debug("Inside DoctorServiceImpl::getById()")
        return doctorRepository.findById(id)
    }
    override fun insert(obj: Doctor): Doctor {
        val responseMessage: Response = validateDoctorDetails(obj)
        return if(responseMessage.message.isEmpty())
           doctorRepository.insert(obj.apply { this.hospitalAffiliation.hospitalDetails=hospitalDetailsRepository.findById(obj.hospitalAffiliation.hospitalDetails.id).get()})
         else
           obj
    }
    override fun update(obj: Doctor): Doctor {
        log.debug("Inside DoctorServiceImpl::update()")
        return if (doctorRepository.existsById(obj.id))
            doctorRepository.save(obj.apply { this.hospitalAffiliation.hospitalDetails = hospitalDetailsRepository.findById(obj.hospitalAffiliation.hospitalDetails.id).get() })
        else
            insert(obj)
    }
    override fun deleteById(id: String): Optional<Doctor> {
        log.debug("Inside DoctorServiceImpl::deleteById()")
        return doctorRepository.findById(id).apply { this.ifPresent{doctorRepository.delete(it)} }
    }
    fun deleteAllDoctorCollections(){
        log.debug("Inside DoctorServiceImpl::deleteAllDoctorCollections()")
        doctorRepository.deleteAll()
    }

    fun validateDoctorDetails(obj: Doctor): Response {
        val responseResult: Response = Response()
        val kieSession = kieContainer.newKieSession()
        kieSession.setGlobal("responseResult", responseResult)
        kieSession.insert(obj) // which object to validate
        kieSession.fireAllRules() // fire all rules defined into drool file (drl)
        kieSession.dispose()
        if (!responseResult.message.isEmpty()) {
            log.info("responseResult code is ${responseResult.code} and message is ${responseResult.message}")
        }
        return responseResult
    }
}