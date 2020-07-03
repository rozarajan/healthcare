package com.perfm.finddoctorapp.util

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
interface BasicCrud<in K,T> {
    fun getAll(pageable: Pageable): Page<T>
    fun getById(id:K): Optional<T>
    fun insert(obj:T):T
    fun update(obj:T):T
    fun deleteById(id: K): Optional<T>
}