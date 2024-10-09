package ru.practicum.android.diploma.vacancy.data.network

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.util.network.Response
import ru.practicum.android.diploma.vacancy.data.dto.AddressDto
import ru.practicum.android.diploma.vacancy.data.dto.AreaDto
import ru.practicum.android.diploma.vacancy.data.dto.EmployerDto
import ru.practicum.android.diploma.vacancy.data.dto.EmploymentDto
import ru.practicum.android.diploma.vacancy.data.dto.ExperienceDto
import ru.practicum.android.diploma.vacancy.data.dto.KeySkillDto
import ru.practicum.android.diploma.vacancy.data.dto.SalaryDto
import ru.practicum.android.diploma.vacancy.data.dto.ScheduleDto

data class VacancyDetailsResponse(
    val id: String,
    val name: String,
    val salary: SalaryDto?,
    val employer: EmployerDto?,
    val area: AreaDto,
    val address: AddressDto?,
    val experience: ExperienceDto?,
    val schedule: ScheduleDto?,
    val employment: EmploymentDto?,
    val description: String,
    @SerializedName("alternate_url") val alternateUrl: String,
    @SerializedName("key_skills") val keySkills: List<KeySkillDto>,
) : Response()
