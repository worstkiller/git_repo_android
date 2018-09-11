package com.incred.gitrepo.data

import android.util.Log
import com.incred.gitrepo.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by incred on 11/9/18.
 */
class NetworkManager(token: String?) : Interceptor {

    val TAG = NetworkManager::class.java.canonicalName
    val tokenValue = token
    val TOKEN_KEY = "api-key"

    /**
     * interceptor for adding the header
     */
    override fun intercept(chain: Interceptor.Chain?): Response {
        Log.d(TAG, "interceptor body")
        val request = chain!!.request()
        var builder = request.newBuilder()
        if (tokenValue != null) {
            builder = chain.request().newBuilder().addHeader(TOKEN_KEY, tokenValue)
        }
        return chain.proceed(builder.build())
    }

    /**
     * web service instance
     */
    val instance: WebService by lazy {
        val okhttp: OkHttpClient.Builder = OkHttpClient.Builder().addInterceptor(this)
        if (BuildConfig.DEBUG) {
            okhttp.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
            okhttp.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }
        val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttp.build())
                .build()
        retrofit.create(WebService::class.java)
    }

}