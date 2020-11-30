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

class EditAlbumActivity : AppCompatActivity() {

    lateinit var editAlbumButton : Button
    lateinit var editAlbumTitle : EditText
    lateinit var editReleaseDate : EditText
    lateinit var album : Album

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_album)

        val album_id = intent.getIntExtra("album_id", 0 )
        val databaseHandler = AlbumsTableHandler(this)
        album = databaseHandler.readOne(album_id)

        editAlbumTitle = findViewById(R.id.editAlbumTitle)
        editReleaseDate = findViewById(R.id.editReleaseDate)
        editAlbumButton = findViewById(R.id.editAlbumButton)

        editAlbumTitle.setText(album.album_title)
        editReleaseDate.setText(album.release_date)

        editAlbumButton.setOnClickListener{
            val album_title = editAlbumTitle.text.toString()
            val release_date = editReleaseDate.text.toString()

            val updated_song = Album(id = album.id, album_title = album_title, release_date = release_date)

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