package com.perfm.finddoctorapp.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.util.*

@Document data class Doctor(@Id val id: String,
                            val firstName : String,
                            val lastName : String,
                            var professionalStatement : String,
                            val practicingFrom: LocalDate,
                            var specialization: Specialization,
                            var doctorSpecialization: DoctorSpecialization,
                            var qualification: Qualification,
                            var hospitalAffiliation: HospitalAffiliation
)

data class Specialization(val id : String, val specializationName: String)
data class DoctorSpecialization(val id:String, val doctorId: String, val specializationId: String)
data class Qualification(val id:String,val doctorId:String,val qualificationName:String,val institutionName:String,val procurementYear:LocalDate)
data class HospitalAffiliation(val id:String,val doctorId:String,var hospitalDetails: HospitalDetails, val startDate:LocalDate,val endDate:LocalDate)

@Document data class HospitalDetails(@Id val id:String, var hospitalName:String, val city:String, val country:String)

data class Response (var code: Int = 0){
    var message : String = ""

}