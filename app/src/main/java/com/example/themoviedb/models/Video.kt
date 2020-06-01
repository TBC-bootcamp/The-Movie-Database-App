package com.example.themoviedb.models

class Video {

    lateinit var results: ArrayList<VideoList>

    class VideoList {
        lateinit var key: String
        var size = 0
        lateinit var type: String
    }
}