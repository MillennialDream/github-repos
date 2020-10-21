package com.sbnri.assignment.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.sbnri.assignment.data.Repository
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainViewModel(private val repository: Repository) : ViewModel() {

    fun getRepos() = repository.getSearchResultStream()
        .cachedIn(viewModelScope)

}