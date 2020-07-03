package com.perfm.finddoctorapp.util

import java.time.LocalDate

class Utils{

fun getFormattedDate(date : String) : LocalDate{
   return LocalDate.parse(date,Objects.dateFormat)
}

}