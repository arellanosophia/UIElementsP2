package com.example.uielementsp2.models

class Album (var id: Int = 0, var album_title : String, var release_date : String){
    override fun toString(): String {
        return "Album Title: ${album_title}, Release Date: ${release_date}"
    }
}