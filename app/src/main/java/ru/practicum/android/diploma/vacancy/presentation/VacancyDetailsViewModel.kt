package ru.practicum.android.diploma.vacancy.presentation

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.favorite.domain.api.FavoriteVacancyInteractor
import ru.practicum.android.diploma.util.network.HttpStatusCode.OK
import ru.practicum.android.diploma.vacancy.domain.api.GetVacancyDetailsInteractor
import ru.practicum.android.diploma.vacancy.domain.entity.Vacancy

class VacancyDetailsViewModel(
    private val vacancyId: String,
    private val interactor: GetVacancyDetailsInteractor,
    private val favoriteInteractor: FavoriteVacancyInteractor
) : ViewModel() {

    private var isFavorite = MutableLiveData<Boolean>()
    private val vacancyState = MutableLiveData<VacancyScreenState>()
    private var vacancy: Vacancy? = null

    fun getVacancyState(): LiveData<VacancyScreenState> = vacancyState
    fun getIsFavorite(): LiveData<Boolean> = isFavorite

    init {
        loadVacancyDetails(vacancyId)
    }

    private fun loadVacancyDetails(vacancyId: String) {
        renderState(VacancyScreenState.LoadingState)
        viewModelScope.launch(Dispatchers.IO) {
            interactor.getVacancyDetails(vacancyId)
                .collect { result ->
                    when (result.second) {
                        null,
                        OK -> {
                            processSuccessResult(result.first)
                            isFavorite.postValue(result.first?.isFavorite)
                            vacancy = result.first
                        }

                        else -> renderState(VacancyScreenState.NetworkErrorState)
                    }
                }
        }

    }

    private fun renderState(state: VacancyScreenState) {
        vacancyState.postValue(state)
    }

    private var vacancyUrl = ""
    private fun processSuccessResult(vacancy: Vacancy?) {
        if (vacancy == null) {
            renderState(VacancyScreenState.EmptyState)
        } else {
            vacancyUrl = vacancy.vacancyUrl
            renderState(VacancyScreenState.ContentState(vacancy))
        }
    }

    fun share(context: Context) {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, vacancyUrl)
            type = "text/plain"
        }
        val share = Intent.createChooser(intent, null)
        share.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(share)
    }
    fun onFavoriteClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            vacancy?.let {
                val updatedVacancy = it.copy(isFavorite = !it.isFavorite)

                if (updatedVacancy.isFavorite) {
                    favoriteInteractor.insertVacancy(updatedVacancy)
                } else {
                    favoriteInteractor.deleteVacancyById(updatedVacancy.id)
                }
                vacancy = updatedVacancy
                isFavorite.postValue(updatedVacancy.isFavorite)
            }
        }
    }

}
