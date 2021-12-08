package com.upt.cti.smartwallet.model

import java.io.Serializable

class ExpenseItem(
    var price: String = "",
    var name: String? = "",
    val date: String? = "",
    val time: String? = "",
    var category: String? = "",
) : Serializable {

}
