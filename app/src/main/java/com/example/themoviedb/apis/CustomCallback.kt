package com.example.themoviedb.apis

import retrofit2.Response

interface CustomCallback {
    fun onResponse(body: String, response: Response<String>)
    fun onFailure(error: String){}
}