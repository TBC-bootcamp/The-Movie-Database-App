package com.example.themoviedb.ui.details_page

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.themoviedb.R
import com.example.themoviedb.apis.CustomCallback
import com.example.themoviedb.apis.HttpRequests
import com.example.themoviedb.databinding.ActivityTvShowDetailsBinding
import com.example.themoviedb.models.Movie
import com.example.themoviedb.models.TVShow
import com.example.themoviedb.models.Video
import com.example.themoviedb.recyclerviews.CreatorsRecyclerViewAdapter
import com.example.themoviedb.recyclerviews.MovieCastRecyclerAdapter
import com.example.themoviedb.ui.details_page.viewmodels.TVShowsViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_tv_show_details.*
import retrofit2.Response

class TvShowDetailsActivity : AppCompatActivity() {
    // Creators
    private lateinit var creatorsAdapter: CreatorsRecyclerViewAdapter
    private var creators = ArrayList<TVShow.Creator>()

    // Cast
    private lateinit var castAdapter: MovieCastRecyclerAdapter
    private var cast = ArrayList<Movie.Cast>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Binding
        val binding: ActivityTvShowDetailsBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_tv_show_details)

        val tvID = intent.getIntExtra("TVShowID", 0)

        // RecyclerView for Creators
        creatorsRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        creatorsAdapter = CreatorsRecyclerViewAdapter(creators)
        creatorsRecyclerView.adapter = creatorsAdapter

        // RecyclerView for Cast
        tvShowCastRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        castAdapter = MovieCastRecyclerAdapter(cast)
        tvShowCastRecyclerView.adapter = castAdapter

        HttpRequests.getTVShowDetails(tvID, object : CustomCallback {
            override fun onResponse(body: String, response: Response<String>) {
                val tvShow = Gson().fromJson(body, TVShow::class.java)
                binding.model = TVShowsViewModel(tvShow)

                tvShow.creatorsList.forEach {
                    creators.add(it)
                }

                tvShow.credits.cast.forEach {
                    cast.add(it)
                }

                creatorsAdapter.notifyDataSetChanged()
                castAdapter.notifyDataSetChanged()
            }

        })

        // Get Trailer and direct to Youtube
        trailerBtn.setOnClickListener {
            HttpRequests.getVideos("tv", tvID, object : CustomCallback {
                override fun onResponse(body: String, response: Response<String>) {
                    val video = Gson().fromJson(body, Video::class.java)

                    val intentUrl = Intent(Intent.ACTION_VIEW)
                    intentUrl.data = Uri.parse("https://www.youtube.com/watch?v=${video.results[0].key}")
                    startActivity(intentUrl)

                }

            })
        }


    }
}
