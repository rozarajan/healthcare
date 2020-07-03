package com.perfm.finddoctorapp.controller

import com.perfm.finddoctorapp.messageservice.MessageProducer
import com.perfm.finddoctorapp.model.Doctor
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/doctor")
class DoctorAppController(private val messageProducer: MessageProducer) {

    @PostMapping("/publish") fun publishDoctorDetails(@RequestBody doctor: Doctor) : String = messageProducer.sendDoctorDetails(doctor)

}