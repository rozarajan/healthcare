package com.perfm.finddoctorapp.repository

import com.perfm.finddoctorapp.model.Doctor
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface DoctorRepository : MongoRepository<Doctor,String>