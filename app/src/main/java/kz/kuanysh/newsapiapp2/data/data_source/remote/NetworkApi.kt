package kz.kuanysh.newsapiapp2.data.data_source.remote

import kz.kuanysh.newsapiapp2.data.data_source.remote.entety.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

private const val NEWS_API_KEY = "28d3553d20de452a9a7dbca4940dcdd9"

interface NetworkApi {

    @GET("v2/everything/")
    suspend fun getEverything(
        @Query("q") searchQuery: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("apiKey") apyKey: String? = NEWS_API_KEY
    ): NewsResponse
}