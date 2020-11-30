package com.example.uielementsp2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.uielementsp2.models.Song

class AddSongActivity : AppCompatActivity() {
    lateinit var addSongButton : Button
    lateinit var title : EditText
    lateinit var artist : EditText
    lateinit var album : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_song)

        val databaseHandler = SongsTableHandler(this)

        title = findViewById(R.id.addSongTitle)
        artist = findViewById(R.id.addSongArtist)
        album = findViewById(R.id.addSongAlbum)

        addSongButton = findViewById(R.id.addSongButton)
        addSongButton.setOnClickListener{
            val title = title.text.toString()
            val artist = artist.text.toString()
            val album = album.text.toString()

            val song = Song(title = title, artist = artist, album = album)

            if(databaseHandler.create(song)){
                Toast.makeText(applicationContext, "Song was added.", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(applicationContext, "Something went wrong!", Toast.LENGTH_SHORT).show()
            }
            clearFields()
        }
    }
    fun clearFields(){
        title.text.clear()
        artist.text.clear()
        album.text.clear()
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
            else ->{
                super.onOptionsItemSelected(item)
            }
        }
    }
}