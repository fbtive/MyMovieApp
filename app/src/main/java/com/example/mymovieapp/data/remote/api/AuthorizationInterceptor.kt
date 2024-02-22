package com.example.mymovieapp.data.remote.api

import android.content.Context
import com.example.mymovieapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthorizationInterceptor constructor(val context: Context): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer ${BuildConfig.BEARER_TOKEN}")
            .addHeader("accept", "application/json")
            .build()

        val response: Response = chain.proceed(request)

        return response
    }
}