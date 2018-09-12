package com.incred.gitrepo.ui.home

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.incred.gitrepo.R
import com.incred.gitrepo.callback.HomeNavigator
import com.incred.gitrepo.callback.ServerResponse
import com.incred.gitrepo.data.ApiProvider
import com.incred.gitrepo.data.Repository
import com.incred.gitrepo.model.GitRepo
import com.incred.gitrepo.model.GitRepoResponse
import com.incred.gitrepo.utils.Utility

/**
 * Created by incred on 11/9/18.
 */
class HomeViewModel(navigator: HomeNavigator) : ViewModel() {

    val errorStatus: MutableLiveData<Int> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val apiProvider = ApiProvider(Repository.getApi(null))
    val adapter = HomeAdapter(navigator)
    val originalList = ArrayList<GitRepo>()

    /**
     * gets a list of git repo with given parameters
     * @param topics
     * @param sort
     * @param order
     */
    fun getGitRepo(topics: String, sort: String, order: String) {
        isLoading.value = true
        apiProvider.getGitRepo(topics, sort, order, object : ServerResponse {
            override fun onModel(model: Any) {
                super.onModel(model)
                //got successful response
                isLoading.value = false
                if (model != null) {
                    val gitResponse = model as GitRepoResponse
                    if (gitResponse.total_count > 0) {
                        updateData(gitResponse.items)
                        errorStatus.value = null
                        originalList.addAll(gitResponse.items)
                    } else {
                        errorStatus.value = R.string.error_no_repo_found
                        adapter.clear()
                    }
                } else {
                    errorStatus.value = R.string.error_git_repo
                }
            }

            override fun onError() {
                super.onError()
                //server failed to response
                isLoading.value = false
                errorStatus.value = R.string.error_git_repo
            }

            override fun onApiFailed() {
                super.onApiFailed()
                //call not made
                isLoading.value = false
                errorStatus.value = R.string.error_internet_connection
            }
        })
    }

    /**
     * handle the data here when received from api
     */
    private fun updateData(listOfRepo: ArrayList<GitRepo>) {
        adapter.updateData(listOfRepo)
    }

    /**
     * this searches in existing data for matches and returns if found
     */
    fun sortResults(query: String) {
        try {
            if (query.isEmpty()) {
                adapter.updateData(originalList)
            } else {
                adapter.updateData(Utility.getSortedResult(query, originalList))
            }
        } catch (exp: Exception) {
            exp.printStackTrace()
        }
    }
}