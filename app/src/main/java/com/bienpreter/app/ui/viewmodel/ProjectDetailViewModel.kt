package com.bienpreter.app.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bienpreter.app.data.model.Project
import com.bienpreter.app.data.repository.ProjectRepository
import kotlinx.coroutines.launch

class ProjectDetailViewModel : ViewModel() {

    private val repository = ProjectRepository()

    private val _project = MutableLiveData<Project?>()
    val project: LiveData<Project?> = _project

    fun loadProject(id: String) {
        viewModelScope.launch {
            repository.getProjectById(id).fold(
                onSuccess = { _project.value = it },
                onFailure = { _project.value = null }
            )
        }
    }
}
