package com.example.uielementsp2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.uielementsp2.models.Album
import com.google.android.material.snackbar.Snackbar

class AlbumActivity : AppCompatActivity() {
    var modal = ArrayList<Modal>()
    var names = arrayOf("Nothing Happens" , "Remote" , "Spring")
    var images = intArrayOf(R.drawable.nothing_happens , R.drawable.remote , R.drawable.spring, R.drawable.uc)
    var titlesArray : ArrayList<String> = ArrayList()

    lateinit var albumsTableHandler: AlbumsTableHandler
    lateinit var albums : MutableList<Album>
    lateinit var album_titles : MutableList<String>
    lateinit var gridView: GridView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)

        albumsTableHandler = AlbumsTableHandler(this)
        albums = albumsTableHandler.read()
        album_titles = albumsTableHandler.title()

        for(i in names){
            titlesArray.add(i)
        }
        for(i in album_titles){
            titlesArray.add(i)
        }
        for(i in images.indices){
            modal.add(Modal(titlesArray[i],images[i]))
        }
        for(i in titlesArray.indices){
            if(i>3){
                modal.add(Modal(titlesArray[i], images[3]))
            }
        }

        gridView = findViewById(R.id.gridView)
        var adapter = CustomAdapter(modal, this)
        gridView.adapter = adapter
        adapter.notifyDataSetChanged()


        gridView.onItemClickListener = OnItemClickListener { _ , view , position , id ->
            val intent = Intent(this , AlbumDetailsActivity::class.java)
            intent.putExtra("position" , titlesArray[position])
            startActivity(intent)
        }
        registerForContextMenu(gridView)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.go_to_queue_page -> {
                val intent = Intent(this, QueueActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.go_to_songs -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.go_to_albums -> {
                startActivity(Intent(this, AlbumActivity::class.java))
                true
            }
            R.id.add_song -> {
                startActivity(Intent(this, AddSongActivity::class.java))
                true
            }
            R.id.add_album -> {
                startActivity(Intent(this, AddAlbumActivity::class.java))
                true
            }
            else ->{
                super.onOptionsItemSelected(item)
            }
        }
    }
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.album, menu)

    }override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId) {
            R.id.edit_album -> {
                val album_id = albums[info.position-3].id
                val intent = Intent(this,EditAlbumActivity::class.java)
                intent.putExtra("album_id",album_id)
                startActivity(intent)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}


