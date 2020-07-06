package com.perfm.finddoctorapp.service

import com.perfm.finddoctorapp.model.HospitalDetails
import com.perfm.finddoctorapp.model.NewResponse
import com.perfm.finddoctorapp.model.NewResult
import com.perfm.finddoctorapp.model.Response
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
class HospitalService(@Autowired val kieContainer: KieContainer, @Autowired val hospitalDetailsRepository: HospitalDetailsRepository):BasicCrud<String,HospitalDetails> {
    private val log = LoggerFactory.getLogger(HospitalService::class.java)

    override fun getAll(pageable: Pageable): Page<HospitalDetails> {
        log.debug("Inside HospitalService::getAll()")
        return hospitalDetailsRepository.findAll(pageable)
    }

    override fun getById(id: String): Optional<HospitalDetails> {
        log.debug("Inside HospitalService::getById()")
        return hospitalDetailsRepository.findById(id)
    }

    override fun insert(obj: HospitalDetails): HospitalDetails {
        log.debug("Inside HospitalService::insert()")

        var responseResult = Response(0)
        val kieSession = kieContainer.newKieSession()
        var resultNewMessage = NewResult("default message")
        try {
            kieSession.setGlobal("responseResult", responseResult)
            if (!responseResult.message.isEmpty()) {
                log.info("responseResult code is ${responseResult.code} and message is ${responseResult.message}")
            }
            kieSession.insert(obj) // which object to validate
            kieSession.insert(resultNewMessage)
            kieSession.fireAllRules() // fire all rules defined into drool file (drl)
            kieSession.dispose()
        }catch (ex: Exception){
            println(ex)
        }
        if (!responseResult.message.isEmpty()){
            log.info("responseResult code is ${responseResult.code} and message is ${responseResult.message}")
        }
        return if (responseResult.message.isEmpty())
            hospitalDetailsRepository.insert(obj)
        else
            hospitalDetailsRepository.insert(obj)
    }

    @Throws(Exception::class)
    override fun update(obj: HospitalDetails): HospitalDetails {
        log.debug("Inside HospitalService::update()")
        if (hospitalDetailsRepository.existsById(obj.id)) {
            return hospitalDetailsRepository.save(obj)
        } else {
            throw object : Exception("Hospital not found") {}
        }
    }

    override fun deleteById(id: String): Optional<HospitalDetails> {
        log.debug("Inside HospitalService::deleteById()")
        return hospitalDetailsRepository.findById(id).apply {
            this.ifPresent { hospitalDetailsRepository.delete(it) }
        }
    }

    fun deleteAllHospitalCollections(){
        log.debug("Inside HospitalService::()")
        hospitalDetailsRepository.deleteAll()
    }

//    fun validateHospitalDetails(hospitalDetails: HospitalDetails): Response{
//        val responseResult: Response = Response()
//        val kieSession = kieContainer.newKieSession()
//        kieSession.setGlobal("responseResult", responseResult)
//        kieSession.insert(hospitalDetails) // which object to validate
//        kieSession.fireAllRules() // fire all rules defined into drool file (drl)
//        kieSession.dispose()
//        if (!responseResult.message.isEmpty()){
//            log.info("responseResult code is ${responseResult.code} and message is ${responseResult.message}")
//        }
//        return responseResult
//    }
}