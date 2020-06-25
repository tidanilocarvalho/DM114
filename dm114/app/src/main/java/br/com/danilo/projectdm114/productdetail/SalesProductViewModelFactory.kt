package br.com.danilo.projectdm114.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SalesProductViewModelFactory(private val code: String) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SalesProductViewModel::class.java)) {
            return SalesProductViewModel(code) as T
        }

        throw IllegalArgumentException("The SalesProductViewModel class is required")
    }

}