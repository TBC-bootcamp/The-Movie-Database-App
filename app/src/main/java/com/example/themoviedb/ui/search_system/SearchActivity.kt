package com.example.themoviedb.ui.search_system

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.themoviedb.R
import com.example.themoviedb.apis.CustomCallback
import com.example.themoviedb.apis.HttpRequests
import com.example.themoviedb.models.TrendingMovies
import com.example.themoviedb.ui.details_page.MovieDetailsActivity
import com.example.themoviedb.recyclerviews.TrendingMoviesRecyclerAdapter
import com.example.themoviedb.recyclerviews.ViewItemClickListener
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_search.*
import retrofit2.Response

class SearchActivity : AppCompatActivity() {

    private var movieList = ArrayList<TrendingMovies.TrendingMoviesList>()
    private lateinit var searchAdapter: TrendingMoviesRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val query = intent.getStringExtra("SearchQuery") as String

        searchEditText.text = Editable.Factory.getInstance().newEditable(query)
        HttpRequests.getSearchMovies(query, callback)

        searchRecyclerView.layoutManager = GridLayoutManager(this, 2)
        searchAdapter = TrendingMoviesRecyclerAdapter(movieList, itemClickListener)
        searchRecyclerView.adapter = searchAdapter

        search.setOnClickListener {
            movieList.clear()
            searchAdapter.notifyDataSetChanged()
            val searchString = searchEditText.text.toString()
            HttpRequests.getSearchMovies(searchString, callback)
        }
    }

    private val callback = object : CustomCallback {
        override fun onResponse(body: String, response: Response<String>) {
            val movie = Gson().fromJson(body, TrendingMovies::class.java)
            movie.movieList.forEach {
                movieList.add(it)
            }
            val resultMetadata = "result : ${movie.movieList.size} movie(s) has been found"
            searchResultMeta.text = resultMetadata

            if (movieList.size == 0){
                searchResultMeta.text = ""
                searchRecyclerView.visibility = View.GONE
                noDataFoundImg.visibility = View.VISIBLE
                noDataFoundImg.setImageResource(R.mipmap.img_no_data_found)
            } else {
                searchRecyclerView.visibility = View.VISIBLE
                noDataFoundImg.visibility = View.GONE
            }

            Toast.makeText(applicationContext, "Searching...", Toast.LENGTH_LONG).show()

            searchAdapter.notifyDataSetChanged()
        }

    }

    private val itemClickListener = object : ViewItemClickListener {
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
