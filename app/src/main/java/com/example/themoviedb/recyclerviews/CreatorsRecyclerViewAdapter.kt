package com.example.themoviedb.recyclerviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.themoviedb.R
import com.example.themoviedb.apis.ApiPaths
import com.example.themoviedb.models.TVShow
import kotlinx.android.synthetic.main.tv_show_creators_recycler_item_layout.view.*

class CreatorsRecyclerViewAdapter(private val creators: ArrayList<TVShow.Creator>) :
    RecyclerView.Adapter<CreatorsRecyclerViewAdapter.CreatorsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CreatorsViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.tv_show_creators_recycler_item_layout, parent, false)
    )

    override fun getItemCount() = creators.size

    override fun onBindViewHolder(holder: CreatorsViewHolder, position: Int) {
        holder.onBind()
    }

    inner class CreatorsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind() {
            val creator = creators[adapterPosition]
            itemView.creatorName.text = creator.name

            Glide
                .with(itemView.context)
                .load(ApiPaths.POSTERS_BASE_URL + "w185" + creator.profilePath)
                .placeholder(R.mipmap.ic_tmdb_logo)
                .centerCrop()
                .into(itemView.tvShowCreatorPoster)
        }
    }
}