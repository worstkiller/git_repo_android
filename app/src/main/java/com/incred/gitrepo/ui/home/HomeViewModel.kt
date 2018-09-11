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

/**
 * Created by incred on 11/9/18.
 */
class HomeViewModel(navigator: HomeNavigator) : ViewModel() {

    val errorStatus: MutableLiveData<Int> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val apiProvider = ApiProvider(Repository.getApi(null))
    val adapter = HomeAdapter(navigator)

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

    private fun updateData(listOfRepo: ArrayList<GitRepo>) {
        adapter.updateData(listOfRepo)
    }
}