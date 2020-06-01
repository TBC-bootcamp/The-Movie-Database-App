package com.example.themoviedb.recyclerviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.themoviedb.R
import com.example.themoviedb.apis.ApiPaths
import com.example.themoviedb.models.TrendingMovies
import kotlinx.android.synthetic.main.tending_movie_recycler_item_layout.view.*

class TrendingMoviesRecyclerAdapter(
        private val moviesList: ArrayList<TrendingMovies.TrendingMoviesList>,
        private val viewItemClickListener: ViewItemClickListener
) :
    RecyclerView.Adapter<TrendingMoviesRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.tending_movie_recycler_item_layout, parent, false)
        )
    }


    override fun getItemCount() = moviesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind()
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind() {
            val movie = moviesList[adapterPosition]

            itemView.trendingMovieName.text = movie.title
            itemView.trendingMovieRating.text = movie.voteAverage.toString()

            Glide
                .with(itemView.context)
                .load(ApiPaths.POSTERS_BASE_URL + "w185" + movie.posterPath)
                .placeholder(R.mipmap.ic_tmdb_logo)
                .centerCrop()
                .into(itemView.trendingMovieImageView)

            viewItemClickListener.onClick(itemView, adapterPosition, movie)
        }
    }
}