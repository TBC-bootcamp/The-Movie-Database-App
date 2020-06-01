package com.example.themoviedb.ui.home_page

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.themoviedb.R
import com.example.themoviedb.ui.search_system.SearchActivity
import com.example.themoviedb.apis.CustomCallback
import com.example.themoviedb.apis.HttpRequests
import com.example.themoviedb.models.TrendingMovies
import com.example.themoviedb.ui.dashboard.DashBoardActivity
import com.example.themoviedb.ui.details_page.MovieDetailsActivity
import com.example.themoviedb.ui.details_page.TvShowDetailsActivity
import com.example.themoviedb.recyclerviews.TrendingMoviesRecyclerAdapter
import com.example.themoviedb.recyclerviews.ViewItemClickListener
import com.example.themoviedb.ui.home_page.viewpager_slider.ViewPagerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Response
import java.io.File

class MainActivity : AppCompatActivity() {
    // Firebase Authentication
    private var mAuth = FirebaseAuth.getInstance()

    // Trending RecyclerViews
    lateinit var trendingMoviesAdapter: TrendingMoviesRecyclerAdapter
    lateinit var trendingTVShowsAdapter: TrendingMoviesRecyclerAdapter
    var movieList = ArrayList<TrendingMovies.TrendingMoviesList>()
    var tvShowsList = ArrayList<TrendingMovies.TrendingMoviesList>()

    // ViewPager
    private var viewPagerTVList = ArrayList<TrendingMovies.TrendingMoviesList>()
    private lateinit var viewPagerAdapter : ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ViewPager
        viewPagerAdapter = ViewPagerAdapter(this, viewPagerTVList, object : ViewItemClickListener {
            override fun onClick(itemView: View, position: Int, movie: TrendingMovies.TrendingMoviesList) {
                itemView.setOnClickListener {
                    startActivity(
                            Intent(applicationContext, TvShowDetailsActivity::class.java)
                                    .putExtra("TVShowID", movie.id)
                    )
                }
            }
        })
        airingTodayViewPager.adapter = viewPagerAdapter
        tabDots.setupWithViewPager(airingTodayViewPager)

        HttpRequests.getAiringTodayTV(object : CustomCallback {
            override fun onResponse(body: String, response: Response<String>) {
                val tvShow = Gson().fromJson(body, TrendingMovies::class.java)
                for (i in 0 until 8){
                    viewPagerTVList.add(tvShow.movieList[i])
                }

                if(viewPagerAdapter.count == 0){
                    viewPagerProgressbar.visibility = View.VISIBLE
                    airingTodayViewPager.visibility = View.GONE
                } else {
                    viewPagerProgressbar.visibility = View.GONE
                    airingTodayViewPager.visibility = View.VISIBLE
                }
                viewPagerAdapter.notifyDataSetChanged()
            }

        })


        // Adapter for Trending Movies
        weeklyTrendingMoviesRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        trendingMoviesAdapter = TrendingMoviesRecyclerAdapter(movieList, onItemClickListener)
        weeklyTrendingMoviesRecyclerView.adapter = trendingMoviesAdapter

        // Adapter for Trending TV Shows
        weeklyTrendingTVsRecyclerView.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        trendingTVShowsAdapter = TrendingMoviesRecyclerAdapter(tvShowsList, onItemClickListener)
        weeklyTrendingTVsRecyclerView.adapter = trendingTVShowsAdapter


        HttpRequests.getTrendingMovies(trendingMovieCallback)
        HttpRequests.getTrendingTVShows(trendingTVShowsCallback)

        // Setting Profile Image
        // Retrieving Profile picture from server and setting to ImageView
        val storage = FirebaseStorage.getInstance()
        val localFile = File.createTempFile("Images", "bmp")
        storage.getReference("images/ ${mAuth.currentUser!!.uid}").getFile(localFile).addOnSuccessListener {
            val image = BitmapFactory.decodeFile(localFile.absolutePath)
            dashboardImageView.setImageBitmap(image)
        }

        // Changing ViewPager elements Manually
        setViewPagerManualSlide()

        search.setOnClickListener {
            val query = searchBarEditText.text.toString()
            if (query.isNotEmpty()){
                val intent = Intent(applicationContext, SearchActivity::class.java)
                intent.putExtra("SearchQuery", query)
                startActivity(intent)
            }
        }

        dashboardImageView.setOnClickListener {
            startActivity(Intent(this, DashBoardActivity::class.java))
        }
    }

    private val trendingMovieCallback = object : CustomCallback {
        override fun onResponse(body: String, response: Response<String>) {
            val movie = Gson().fromJson<TrendingMovies>(body, TrendingMovies::class.java)

            movie.movieList.forEach {
                movieList.add(it)
            }

            trendingMoviesAdapter.notifyDataSetChanged()
        }

    }

    private val trendingTVShowsCallback = object : CustomCallback {
        override fun onResponse(body: String, response: Response<String>) {
            val movie = Gson().fromJson<TrendingMovies>(body, TrendingMovies::class.java)

            for (tv in movie.movieList) {
                tvShowsList.add(tv)
            }

            trendingTVShowsAdapter.notifyDataSetChanged()
        }

    }

    private val onItemClickListener: ViewItemClickListener = object : ViewItemClickListener {
        override fun onClick(
            itemView: View,
            position: Int,
            movie: TrendingMovies.TrendingMoviesList
        ) {
            itemView.setOnClickListener {
                if (movie.mediaType == "movie") {
                    startActivity(
                        Intent(applicationContext, MovieDetailsActivity::class.java)
                            .putExtra("MovieID", movie.id)
                    )
                }
                // Else Statement for Tv series section ---------------------
                else {
                    startActivity(
                        Intent(applicationContext, TvShowDetailsActivity::class.java)
                            .putExtra("TVShowID", movie.id)
                    )
                }
            }
        }

    }

    private fun setViewPagerManualSlide(){
        val handler = Handler()
        val runnable = Runnable {
            if (airingTodayViewPager.currentItem == viewPagerTVList.size - 1){
                airingTodayViewPager.setCurrentItem(0, true)
            } else {
                airingTodayViewPager.setCurrentItem(airingTodayViewPager.currentItem + 1)
            }
        }
        handler.postDelayed(runnable, 8000)
        airingTodayViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 4000)
            }

        })
    }
}
