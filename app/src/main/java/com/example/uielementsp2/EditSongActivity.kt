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

class EditSongActivity : AppCompatActivity() {
    lateinit var editSongButton : Button
    lateinit var editSongTitle : EditText
    lateinit var editSongArtist : EditText
    lateinit var editSongAlbum : EditText
    lateinit var song : Song


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_song)

        val song_id = intent.getIntExtra("song_id", 0 )
        val databaseHandler = SongsTableHandler(this)
        song = databaseHandler.readOne(song_id)

        editSongTitle = findViewById(R.id.editSongTitle)
        editSongArtist = findViewById(R.id.editSongArtist)
        editSongAlbum = findViewById(R.id.editSongAlbum)
        editSongButton = findViewById(R.id.editSongButton)

        editSongTitle.setText(song.title)
        editSongArtist.setText(song.artist)
        editSongAlbum.setText(song.album)

        editSongButton.setOnClickListener{
            val title = editSongTitle.text.toString()
            val artist = editSongArtist.text.toString()
            val album = editSongAlbum.text.toString()

            val updated_song = Song(id = song.id, title = title, artist = artist, album = album)

            if(databaseHandler.update(updated_song)){
                Toast.makeText(applicationContext, "Song was updated.", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(applicationContext, "Something went wrong!", Toast.LENGTH_SHORT).show()
            }
        }
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