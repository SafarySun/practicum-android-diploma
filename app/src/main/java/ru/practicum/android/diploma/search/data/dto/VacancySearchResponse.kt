package ru.practicum.android.diploma.search.data.dto

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.util.network.Response

class VacancySearchResponse(
    val items: List<VacancyItemDto>,
    val found: Int,
    val page: Int,
    val pages: Int,
    @SerializedName("per_page") val perPage: Int,
) : Response()
