package ru.practicum.android.diploma.filters.industries.data.dto

data class IndustryDto(
    val id: String,
    val name: String,
    val isChecked: Boolean,
    val industries: List<IndustryDto>? = null
)
