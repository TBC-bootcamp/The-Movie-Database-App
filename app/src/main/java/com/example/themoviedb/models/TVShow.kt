package com.example.themoviedb.models

import com.google.gson.annotations.SerializedName

class TVShow {
    var id = 0
    lateinit var name : String
    lateinit var overview : String
    // Images
    @SerializedName("backdrop_path")
    lateinit var backdropPath: String
    @SerializedName("poster_path")
    lateinit var posterPath: String

    // Creator List
    @SerializedName("created_by")
    lateinit var creatorsList: ArrayList<Creator>

    // Run Time
    @SerializedName("episode_run_time")
    lateinit var episodeRunTime: Array<Int>

    @SerializedName("first_air_date")
    lateinit var firstAirDate: String
    lateinit var homepage: String

    // Genres
    lateinit var genres : ArrayList<Movie.Genre>

    // Episodes
    @SerializedName("number_of_episodes")
    var numberOfEpisodes = 0
    @SerializedName("number_of_seasons")
    var numberOfSeasons = 0

    // Seasons
    lateinit var seasons : ArrayList<Season>

    // Votes
    @SerializedName("vote_average")
    var voteAverage = 0.0f
    @SerializedName("vote_count")
    var voteCount = 1L

    @SerializedName("credits")
    lateinit var credits : Movie.Credits



    class Season {
        @SerializedName("air_date")
        lateinit var airDate : String
        @SerializedName("episode_count")
        var episodeCount = 0
        lateinit var name : String
        lateinit var overview : String

        @SerializedName("poster_path")
        lateinit var posterPath: String

        @SerializedName("season_number")
        var seasonNumber = 0
    }

    class Creator {
        var id = 0
        lateinit var name: String
        var gender = 0

        @SerializedName("profile_path")
        var profilePath: String? = null
    }
}