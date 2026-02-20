package com.bienpreter.app.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bienpreter.app.data.model.Project
import com.bienpreter.app.data.model.ProjectStats
import com.bienpreter.app.data.repository.ProjectRepository
import kotlinx.coroutines.launch

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

class ProjectsViewModel : ViewModel() {

    private val repository = ProjectRepository()

    private val _projects = MutableLiveData<UiState<List<Project>>>()
    val projects: LiveData<UiState<List<Project>>> = _projects

    private val _stats = MutableLiveData<ProjectStats>()
    val stats: LiveData<ProjectStats> = _stats

    private val _selectedCategory = MutableLiveData<String?>(null)
    val selectedCategory: LiveData<String?> = _selectedCategory

    init {
        loadStats()
        loadProjects()
    }

    private fun loadStats() {
        _stats.value = repository.stats
    }

    fun loadProjects(category: String? = null, status: String? = null) {
        _projects.value = UiState.Loading
        viewModelScope.launch {
            repository.getProjects(category, status).fold(
                onSuccess = { _projects.value = UiState.Success(it) },
                onFailure = { _projects.value = UiState.Error(it.message ?: "Erreur inconnue") }
            )
        }
    }

    fun filterByCategory(category: String?) {
        _selectedCategory.value = category
        loadProjects(category = if (category == "Tous") null else category)
    }

    fun refresh() {
        loadProjects(_selectedCategory.value)
    }
}
