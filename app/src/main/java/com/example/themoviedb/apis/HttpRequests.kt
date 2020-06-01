package com.example.themoviedb.apis

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

object HttpRequests {

    val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
//        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("http://api.themoviedb.org/3/")
        .build()

    val service = retrofit.create(ApiService::class.java)

    private fun onCallback(customCallback: CustomCallback) = object : Callback<String> {
        override fun onResponse(call: Call<String>, response: Response<String>) {
            customCallback.onResponse(response.body().toString(), response)
        }

        override fun onFailure(call: Call<String>, t: Throwable) {
            customCallback.onFailure(t.toString())
        }

    }

    fun getTrendingMovies(callback: CustomCallback) {
        service.getTrendingMovies(ApiPaths.API_KEY).enqueue(onCallback(callback))
    }

    fun getTrendingTVShows(callback: CustomCallback) {
        service.getTrendingTVShows(ApiPaths.API_KEY).enqueue(onCallback(callback))
    }

    fun getMovieDetails(id: Int, callback: CustomCallback) {
        service.getMovieDetails(id.toString(), ApiPaths.API_KEY, "credits")
            .enqueue(onCallback(callback))
    }

    fun getTVShowDetails(id: Int, callback: CustomCallback) {
        service.getTvShowDetails(id.toString(), ApiPaths.API_KEY, "credits")
            .enqueue(onCallback(callback))
    }

    fun getSearchMovies(queryString: String, callback: CustomCallback) {
        service.searchMovies(ApiPaths.API_KEY, queryString).enqueue(onCallback(callback))
    }

    fun getSimilarMovies(movieID : Int, callback: CustomCallback){
        service.similarMovies(movieID.toString(), ApiPaths.API_KEY).enqueue(onCallback(callback))
    }

    fun getVideos(mediaType: String, movieID: Int, callback: CustomCallback){
        service.movieVideos(mediaType, movieID.toString(), ApiPaths.API_KEY).enqueue(onCallback(callback))
    }

    fun getAiringTodayTV(callback: CustomCallback){
        service.getAiringTodayTV(ApiPaths.API_KEY).enqueue(onCallback(callback))
    }


    interface ApiService {

        @GET("tv/airing_today?")
        fun getAiringTodayTV(
                @Query("api_key") apiKey: String
        ): Call<String>

        @GET("trending/movie/week?")
        fun getTrendingMovies(@Query("api_key") apiKey: String): Call<String>

        @GET("trending/tv/week?")
        fun getTrendingTVShows(@Query("api_key") apiKey: String): Call<String>

        @GET("movie/{movie_id}?")
        fun getMovieDetails(
            @Path("movie_id") id: String,
            @Query("api_key") apiKey: String,
            @Query("append_to_response") response: String
        ): Call<String>


        @GET("tv/{tv_id}?")
        fun getTvShowDetails(
            @Path("tv_id") id: String,
            @Query("api_key") apiKey: String,
            @Query("append_to_response") response: String
        ): Call<String>

        @GET("search/movie?")
        fun searchMovies(
            @Query("api_key") apiKey: String,
            @Query("query") queryString: String,
            @Query("include_adult") adult: String = "false"
        ): Call<String>


        @GET("movie/{movie_id}/similar?")
        fun similarMovies(
                @Path("movie_id") id: String,
                @Query("api_key") apiKey: String
        ): Call<String>

        @GET("{media_type}/{movie_id}/videos?")
        fun movieVideos(
                @Path("media_type") mediaType : String,
                @Path("movie_id") id: String,
                @Query("api_key") apiKey: String
        ): Call<String>

    }
}