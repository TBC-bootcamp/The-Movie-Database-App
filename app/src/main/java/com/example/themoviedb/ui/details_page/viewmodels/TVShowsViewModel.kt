package com.example.themoviedb.ui.details_page.viewmodels

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.example.themoviedb.R
import com.example.themoviedb.apis.ApiPaths
import com.example.themoviedb.models.TVShow
import java.lang.StringBuilder

class TVShowsViewModel(var tvShow : TVShow) : ViewModel(){

    companion object {
        @JvmStatic
        @BindingAdapter("glideImageUrlForTVShow")
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
        for (i in tvShow.genres) {
            genresString.append(i.name + ", ")
        }
        return genresString.toString()
    }

    fun duration(): String {
        val runtime = tvShow.episodeRunTime[0]
        val hours = Math.floorDiv(runtime, 60)
        val minutes = runtime - (Math.floorDiv(runtime, 60) * 60)

        return "${hours}h ${minutes}m"
    }

}