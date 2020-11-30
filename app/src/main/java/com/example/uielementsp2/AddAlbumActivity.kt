package com.example.uielementsp2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.uielementsp2.models.Album
import com.example.uielementsp2.models.Song

class AddAlbumActivity : AppCompatActivity() {
    lateinit var addAlbumButton: Button
    lateinit var albumTitle: EditText
    lateinit var releaseDate: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_album)

        val databaseHandler = AlbumsTableHandler(this)

        albumTitle = findViewById(R.id.editTextAlbumTitle)
        releaseDate = findViewById(R.id.editTextRelDate)

        addAlbumButton = findViewById(R.id.addAlbumButton)
        addAlbumButton.setOnClickListener {
            val albumTitle = albumTitle.text.toString()
            val releaseDate = releaseDate.text.toString()

            val album = Album(album_title = albumTitle , release_date = releaseDate)

            if (databaseHandler.create(album)) {
                Toast.makeText(applicationContext , "Album was created." , Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext , "Something went wrong!" , Toast.LENGTH_SHORT).show()
            }
            clearFields()
        }
    }

    fun clearFields() {
        albumTitle.text.clear()
        releaseDate.text.clear()
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
}