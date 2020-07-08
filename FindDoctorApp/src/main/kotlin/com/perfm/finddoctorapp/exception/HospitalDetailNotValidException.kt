package com.perfm.finddoctorapp.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Hospital Details Not Valid")
class HospitalDetailNotValidException: RuntimeException {
    constructor(message: String): super(message){}
}