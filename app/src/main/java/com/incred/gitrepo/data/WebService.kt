package com.incred.gitrepo.data

import com.incred.gitrepo.model.GitRepoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by incred on 11/9/18.
 */
interface WebService {

    /**
     * get list of trending repo related to some topics
     */
    @GET("/search/repositories")
    fun getTrendingRepo(@Query("q") topic: String,
                        @Query("sort") sort: String,
                        @Query("order") order: String): Call<GitRepoResponse>
}