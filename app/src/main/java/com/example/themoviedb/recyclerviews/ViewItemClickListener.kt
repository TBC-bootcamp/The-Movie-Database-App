package com.example.themoviedb.recyclerviews

import android.view.View
import com.example.themoviedb.models.TrendingMovies

interface ViewItemClickListener {
    fun onClick(itemView: View, position : Int, movie : TrendingMovies.TrendingMoviesList)
}