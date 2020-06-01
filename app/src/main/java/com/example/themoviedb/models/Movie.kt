package com.example.themoviedb.models

import com.google.gson.annotations.SerializedName

class Movie {
    var adult = false
    @SerializedName("backdrop_path")
    lateinit var cover : String
    var budget = 0L
    lateinit var genres : ArrayList<Genre>
    lateinit var homepage : String
    var id = 0
    @SerializedName("original_language")
    lateinit var originalLanguage : String
    @SerializedName("original_title")
    lateinit var originalTitle : String
    lateinit var overview : String
    @SerializedName("poster_path")
    lateinit var posterPath : String
    @SerializedName("release_date")
    lateinit var releaseDate : String
    var revenue = 0L
    lateinit var tagline : String
    lateinit var title : String
    lateinit var name : String
    @SerializedName("vote_average")
    var voteAverage = 0.0f
    @SerializedName("vote_count")
    var voteCount = 0
    lateinit var credits : Credits
    var runtime = 0

    class Genre{
        var id = 0
        lateinit var name : String
    }

    class Credits {
        lateinit var cast : ArrayList<Cast>
    }

    class Cast {
        @SerializedName("cast_id")
        var castID = 0
        lateinit var character : String
        @SerializedName("credit_id")
        lateinit var creditID : String
        var gender = 0
        var id = 0
        lateinit var name : String
        @SerializedName("profile_path")
        var profilePath : String = ""
    }
}