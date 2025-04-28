package com.saikrishna.reachmobi.data.remote

import com.saikrishna.reachmobi.data.model.NewsItemDto
import com.saikrishna.reachmobi.data.model.NewsResponse
import com.saikrishna.reachmobi.utils.APIConstants
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(APIConstants.ENDPOINTS.TOP_HEADLINES)
    suspend fun getNewsItems(
        @Query(APIConstants.PARAMS.PAGE) page: Int,
        @Query(APIConstants.PARAMS.COUNTRY) country: String
    ): NewsResponse
}