package com.incred.gitrepo.data

import com.incred.gitrepo.callback.ServerResponse
import com.incred.gitrepo.model.GitRepoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by incred on 11/9/18.
 */
class ApiProvider(api: WebService) {

    private val webService = api

    /**
     * this will make the server call and return the requested response
     */
    private fun getServerCallback(listener: ServerResponse): Callback<Any>? {
        return object : Callback<Any> {
            override fun onResponse(call: Call<Any>?, response: Response<Any>?) {
                if (response!!.isSuccessful && response.body() != null) {
                    listener.onModel(response.body()!!)
                } else {
                    listener.onError()
                }
            }

            override fun onFailure(call: Call<Any>?, t: Throwable?) {
                listener.onApiFailed()
                t!!.printStackTrace()
            }
        }
    }

    /**
     * this makes the git repo api fetch
     */
    fun getGitRepo(topics: String,
                   sort: String,
                   order: String,
                   listener: ServerResponse) {

        webService.getTrendingRepo(topics, sort, order).enqueue(getServerCallback(listener) as Callback<GitRepoResponse>)
    }

}