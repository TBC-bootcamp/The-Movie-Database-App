package com.example.themoviedb.recyclerviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.themoviedb.R
import com.example.themoviedb.apis.ApiPaths
import com.example.themoviedb.models.Movie
import kotlinx.android.synthetic.main.movie_cast_recycler_item_layout.view.*

class MovieCastRecyclerAdapter(private val items: ArrayList<Movie.Cast>) :
    RecyclerView.Adapter<MovieCastRecyclerAdapter.MovieCastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCastViewHolder {
        return MovieCastViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_cast_recycler_item_layout, parent, false)
        )
    }

    override fun getItemCount() = if (items.size >= 25) 25 else items.size


    override fun onBindViewHolder(holder: MovieCastViewHolder, position: Int) {
        holder.onBind()
    }

    inner class MovieCastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind() {
            val castPerson = items[adapterPosition]
            itemView.movieCastName.text = castPerson.name
            itemView.movieCastCharacter.text = castPerson.character

            Glide
                .with(itemView.context)
                .load(ApiPaths.POSTERS_BASE_URL + "w185" + castPerson.profilePath)
                .placeholder(R.mipmap.ic_tmdb_logo)
                .centerCrop()
                .into(itemView.movieCastImageView)

        }
    }

}