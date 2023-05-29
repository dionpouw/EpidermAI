package com.jeflette.epidermai.model

data class Disease(
    val id: Int,
    val name: String,
    val description: String,
    val image: String,
    val listMedicine: List<Medicine>,
)
