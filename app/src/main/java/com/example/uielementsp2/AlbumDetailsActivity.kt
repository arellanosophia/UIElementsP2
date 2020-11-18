package com.example.uielementsp2

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView

class AlbumDetailsActivity : AppCompatActivity() {

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_details)

        val viewName = findViewById<TextView>(R.id.albumTitleTextView)
        val viewImage = findViewById<ImageView>(R.id.albumImageView)

        var albumSongs :Array<String> =arrayOf()
        val position = intent.extras!!.getString("position")

        if (position.equals("Nothing Happens")){
            viewName.text = position
            viewImage.setImageResource(R.drawable.nothing_happens)
            albumSongs = arrayOf("Are You Bored Yet?","Scrawny", "Remember When", "Treacherous Doctor","Do Not Wait","Ice Cold Pool")
        }
        else if (position.equals("Remote")){
            viewName.text = position
            viewImage.setImageResource(R.drawable.remote)
            albumSongs = arrayOf("Wish Me Luck","Coastlines", "Virtual Aerobics","Dig What You Dug","Nobody Gets Me(Like You)")
        }
        else if (position.equals("Spring")){
            viewName.text = position
            viewImage.setImageResource(R.drawable.spring)
            albumSongs = arrayOf("Pictures of Girls","These Days","1980s Horror Film","Ground","Let The Sun In", "It's Only Right")
        }
        var adapter = ArrayAdapter(this , android.R.layout.simple_list_item_1 , albumSongs)
        var albumDetailListView = findViewById<ListView>(R.id.albumDetailListView)
        albumDetailListView.adapter = adapter
    }
}
