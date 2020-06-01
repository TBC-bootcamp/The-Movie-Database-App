package com.example.themoviedb.models

import com.google.gson.annotations.SerializedName

class TrendingMovies {
	@SerializedName("results")
	lateinit var movieList : ArrayList<TrendingMoviesList>

	class TrendingMoviesList {
		var id = 0
		var video : Boolean = false
		@SerializedName("vote_count")
		var voteCount = 0
		@SerializedName("vote_average")
		var voteAverage = 0f

		@SerializedName("title", alternate = ["name"])
		lateinit var title : String
		@SerializedName("release_date", alternate = ["first_air_date"])
		lateinit var releaseDate : String
		@SerializedName("original_Language")
		lateinit var originalLanguage : String

		@SerializedName("genre_ids")
		lateinit var genreIDs : Array<Int>

		var adult = false
		lateinit var overview : String

		@SerializedName("poster_path")
		var posterPath : String? = null
		@SerializedName("media_type")
		lateinit var mediaType : String

		@SerializedName("backdrop_path")
		var backdropPath : String? = null

	}
}