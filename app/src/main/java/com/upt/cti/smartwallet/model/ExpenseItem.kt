package com.upt.cti.smartwallet.model

data class ExpenseItem(
    val price: String = "",
    val name : String? =  "",
    val date : String? = "",
    val time : String? = "",
    val category : String? = "",
)
