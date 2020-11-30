package com.example.uielementsp2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.uielementsp2.models.Song
import com.google.android.material.snackbar.Snackbar
var selectedSong = arrayListOf<String>()
class MainActivity : AppCompatActivity() {

    var songsArray : ArrayList<String> = ArrayList()

    lateinit var songsListView : ListView
    lateinit var songsTableHandler: SongsTableHandler
    lateinit var songs : MutableList<Song>
    lateinit var adapter: ArrayAdapter<String>
    lateinit var titles : MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        songsTableHandler = SongsTableHandler(this)
        songs = songsTableHandler.read()
        titles = songsTableHandler.title()

        var list = arrayOf("Are You Bored Yet?", "Wish Me Luck", "Coastlines", "Pictures of Girls", "These Days", "Scrawny", "1980s Horror Film",
                "Ground", "Visual Aerobics", "Dig What You Dug", "Nobody Gets Me (Like You)", "Let The Sun In", "It's Only Right", "Remember When", "Treacherous Doctor",
                "Do Not Wait", "Ice Cold Pool", "Only Friend", "Sidelines", "Worlds Apart", "What You Like", "I'm Full", "Talk Like That")

        for(song in list){
            songsArray.add(song)
        }
        for(song in titles){
            songsArray.add(song)
        }
        adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songsArray)
        songsListView = findViewById(R.id.songsListView)
        songsListView.adapter = adapter
        registerForContextMenu(songsListView)

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.long_press, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when(item.itemId){
            R.id.add_to_queue -> {
                val songList: ListView = findViewById<ListView>(R.id.songsListView)
                val snackbar: Snackbar = Snackbar.make(songList , "Song added to queue." , Snackbar.LENGTH_SHORT)
                snackbar.setAction("Go To Song Queue" , View.OnClickListener {
                    startActivity(Intent(applicationContext , QueueActivity::class.java))
                })
                snackbar.show()
                selectedSong.add(songsArray[info.position])
                true
            }
            R.id.edit_song -> {
                val song_id = songs[info.position-23].id
                val intent = Intent(applicationContext, EditSongActivity::class.java)
                intent.putExtra("song_id",song_id)
                startActivity(intent)
                true
            }
            R.id.delete_song -> {
                val song = songs[info.position-23]
                if(songsTableHandler.delete(song)){
                    songsArray.removeAt(info.position)
                    adapter.notifyDataSetChanged()
                    Toast.makeText(applicationContext, "Song was deleted.", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(applicationContext, "Something went wrong!", Toast.LENGTH_SHORT).show()
                }
                true
            }
            R.id.add_to_album->{
                val intent = Intent(applicationContext, AlbumListActivity::class.java)
                val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
                var pos = (songsArray[info.position])
                intent.putExtra("song", pos)
                startActivity(intent)
                true
            }
            else-> super.onContextItemSelected(item)
        }
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

