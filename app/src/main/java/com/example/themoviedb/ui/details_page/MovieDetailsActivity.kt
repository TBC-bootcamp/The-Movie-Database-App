package com.example.themoviedb.ui.details_page

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.themoviedb.R
import com.example.themoviedb.apis.CustomCallback
import com.example.themoviedb.apis.HttpRequests
import com.example.themoviedb.models.TrendingMovies
import com.example.themoviedb.databinding.ActivityMovieDetailsBinding
import com.example.themoviedb.models.Movie
import com.example.themoviedb.models.Video
import com.example.themoviedb.recyclerviews.MovieCastRecyclerAdapter
import com.example.themoviedb.recyclerviews.TrendingMoviesRecyclerAdapter
import com.example.themoviedb.recyclerviews.ViewItemClickListener
import com.example.themoviedb.ui.details_page.viewmodels.MovieViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_movie_details.*
import retrofit2.Response

class MovieDetailsActivity : AppCompatActivity() {

    private var castList = ArrayList<Movie.Cast>()
    lateinit var movieCastAdapter: MovieCastRecyclerAdapter
    private var similarMovies = ArrayList<TrendingMovies.TrendingMoviesList>()
    lateinit var similarMoviesAdapter: TrendingMoviesRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMovieDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details)
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        movieCastRecyclerView.layoutManager = linearLayoutManager
        movieCastAdapter = MovieCastRecyclerAdapter(castList)
        movieCastRecyclerView.adapter = movieCastAdapter

        val movieID = intent.getIntExtra("MovieID", 0)

        // API request for Movie Details
        HttpRequests.getMovieDetails(movieID, object : CustomCallback {
            override fun onResponse(body: String, response: Response<String>) {
                val movie = Gson().fromJson<Movie>(body, Movie::class.java)

                binding.movieViewModel = MovieViewModel(movie)
                movie.credits.cast.forEach {
                    castList.add(it)
                }
                movieCastAdapter.notifyDataSetChanged()


            }
        })

        // Get Trailer and direct to Youtube
        trailerBtn.setOnClickListener {
            HttpRequests.getVideos("movie", movieID, object : CustomCallback {
                override fun onResponse(body: String, response: Response<String>) {
                    val video = Gson().fromJson(body, Video::class.java)

                    val intentUrl = Intent(Intent.ACTION_VIEW)
                    if (video.results.size != 0) {
                        intentUrl.data = Uri.parse("https://www.youtube.com/watch?v=${video.results[0].key}")
                        startActivity(intentUrl)
                    } else {
                        Snackbar.make(snackBar_action, "No Video Found. Sorry !", Snackbar.LENGTH_LONG)
                                .setBackgroundTint(Color.RED).show()
                    }

                }
            })
        }

        // RecyclerView for Similar Movies
        similarMoviesRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        similarMoviesAdapter = TrendingMoviesRecyclerAdapter(similarMovies, onItemClick)
        similarMoviesRecyclerView.adapter = similarMoviesAdapter

        // API request for Similar Movies
        HttpRequests.getSimilarMovies(movieID, object : CustomCallback {
            override fun onResponse(body: String, response: Response<String>) {
                val similarMovieModel = Gson().fromJson(body, TrendingMovies::class.java)
                similarMovieModel.movieList.forEach {
                    similarMovies.add(it)
                }

                similarMoviesAdapter.notifyDataSetChanged()
            }

        })

    }

    private val onItemClick = object : ViewItemClickListener {
        override fun onClick(itemView: View, position: Int, movie: TrendingMovies.TrendingMoviesList) {
            itemView.setOnClickListener {
                startActivity(
                        Intent(applicationContext, MovieDetailsActivity::class.java)
                                .putExtra("MovieID", movie.id)
                )
            }
        }

    }
}
