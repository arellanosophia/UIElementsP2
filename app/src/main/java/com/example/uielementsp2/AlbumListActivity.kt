package com.example.uielementsp2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.uielementsp2.models.Album

var albumList : ArrayList<String> = ArrayList()

class AlbumListActivity : AppCompatActivity() {
    lateinit var albumTableHandler: AlbumsTableHandler
    lateinit var albums: MutableList<Album>
    lateinit var showAlbumsListView : ListView
    lateinit var titles : MutableList<String>
    lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_list)

        albumTableHandler = AlbumsTableHandler(this)
        albums = albumTableHandler.read()
        titles = albumTableHandler.title()

        showAlbumsListView = findViewById(R.id.albumListView)
        for (album in titles) {
            albumList.add(album)
        }
        adapter = ArrayAdapter(this , android.R.layout.simple_list_item_1 , albumList)
        showAlbumsListView.adapter = adapter
        adapter.notifyDataSetChanged()

        val pos: String = intent.extras!!.getString("song").toString()
        showAlbumsListView.onItemClickListener = AdapterView.OnItemClickListener { _ , _ , position , id ->
            val intent = Intent(this , AlbumDetailsActivity::class.java)
            intent.putExtra("song" , pos)
            intent.putExtra("album_title", albumList[position])
            startActivity(intent)
        }
    }
}