package com.perfm.finddoctorapp.repository

import com.perfm.finddoctorapp.model.HospitalDetails
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface HospitalDetailsRepository : MongoRepository<HospitalDetails,String>