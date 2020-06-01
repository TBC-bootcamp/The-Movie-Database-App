package com.example.themoviedb.ui.details_page.viewmodels

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.example.themoviedb.R
import com.example.themoviedb.apis.ApiPaths
import com.example.themoviedb.models.Movie
import java.lang.StringBuilder

data class MovieViewModel(var movie: Movie) : ViewModel() {

    companion object {
        @JvmStatic
        @BindingAdapter("glideImageUrl")
        fun loadImage(view: ImageView, posterPath: String?) {
            if (!posterPath.isNullOrEmpty()) {
                Glide.with(view.context)
                    .load(ApiPaths.POSTERS_BASE_URL + "w500" + posterPath)
                    .placeholder(R.mipmap.ic_tmdb_logo)
                    .centerCrop()
                    .into(view)
            }

        }
    }

    fun genres(): String {
        val genresString = StringBuilder()
        for (i in movie.genres) {
            genresString.append(i.name + ", ")
        }
        return genresString.toString()

    }

    fun duration(): String {
        val hours = Math.floorDiv(movie.runtime, 60)
        val minutes = movie.runtime - (Math.floorDiv(movie.runtime, 60) * 60)

        return "${hours}h ${minutes}m"
    }

    fun adultContent() = if (movie.adult) "Adult" else "R"

    fun adultContentDescription() = if (movie.adult) "Not For Kids" else "For Everyone"
}
