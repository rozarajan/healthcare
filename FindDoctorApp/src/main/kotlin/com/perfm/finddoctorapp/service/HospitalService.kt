package com.perfm.finddoctorapp.service

import com.perfm.finddoctorapp.model.HospitalDetails
import com.perfm.finddoctorapp.repository.HospitalDetailsRepository
import com.perfm.finddoctorapp.util.BasicCrud
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class HospitalService(@Autowired val hospitalDetailsRepository: HospitalDetailsRepository):BasicCrud<String,HospitalDetails> {
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
       return hospitalDetailsRepository.insert(obj)
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
}