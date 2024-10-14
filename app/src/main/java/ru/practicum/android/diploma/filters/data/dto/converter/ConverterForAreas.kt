package ru.practicum.android.diploma.filters.data.dto.converter

import ru.practicum.android.diploma.filters.domain.models.Area
import ru.practicum.android.diploma.vacancy.data.dto.AreaDto

class ConverterForAreas {
    fun map(dto: AreaDto): Area {
        return Area(
            id = dto.id,
            name = dto.name,
            parentId = dto.parentId,
            areas = dto.areas.map { areaDto ->
                map(areaDto)
            }
        )
    }
}
