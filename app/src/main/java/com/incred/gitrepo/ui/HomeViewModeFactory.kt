package com.incred.gitrepo.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.incred.gitrepo.callback.HomeNavigator
import com.incred.gitrepo.ui.home.HomeViewModel

/**
 * Created by incred on 11/9/18.
 */
class HomeViewModeFactory(private val navigator: HomeNavigator) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(navigator) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}