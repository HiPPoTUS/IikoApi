package com.example.iikoapi.start

import androidx.lifecycle.ViewModel
import com.example.iikoapi.main.MainViewModel
import com.example.iikoapi.utils.Repository
import javax.inject.Inject

class StartFragmentViewModel @Inject constructor(private val repository: Repository, private val mainViewModel: MainViewModel)
    : ViewModel(){
}