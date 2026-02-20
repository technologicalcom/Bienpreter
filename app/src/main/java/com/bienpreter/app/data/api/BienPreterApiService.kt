package com.bienpreter.app.data.api

import com.bienpreter.app.data.model.Project
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BienPreterApiService {

    @GET("api/projects")
    suspend fun getProjects(
        @Query("page") page: Int = 1,
        @Query("orderBy") orderBy: String? = null,
        @Query("orderType") orderType: String? = null,
        @Query("status") status: String? = null,
        @Query("category") category: String? = null
    ): Response<List<Project>>

    @GET("api/projects/{id}")
    suspend fun getProjectDetail(@Path("id") id: String): Response<Project>
}
