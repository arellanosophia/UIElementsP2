package com.example.uielementsp2

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar


var selectedSong = arrayListOf<String>()
var songsArray = arrayOf(
        "Are You Bored Yet?",
        "Wish Me Luck",
        "Coastlines",
        "Pictures of Girls",
        "These Days",
        "Scrawny",
        "1980s Horror Film",
        "Ground",
        "Visual Aerobics",
        "Dig What You Dug",
        "Nobody Gets Me (Like You)",
        "Let The Sun In",
        "It's Only Right",
        "Remember When",
        "Treacherous Doctor",
        "Do Not Wait",
        "Ice Cold Pool",
        "Only Friend",
        "Sidelines",
        "Worlds Apart",
        "What You Like", "" +
        "I'm Full",
        "Talk Like That"
)
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songsArray)
        val productsListView = findViewById<ListView>(R.id.productsListView)
        productsListView.adapter = adapter
        registerForContextMenu(productsListView)

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
        return when(item.itemId){
            R.id.add_to_queue -> {
                val songList: ListView = findViewById<ListView>(R.id.productsListView)
                val snackbar: Snackbar = Snackbar.make(songList , "Song added to queue." , Snackbar.LENGTH_SHORT)
                snackbar.setAction("Go To Song Queue" , View.OnClickListener {
                    startActivity(Intent(applicationContext , QueueActivity::class.java))
                })
                snackbar.show()
                val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
                selectedSong.add(songsArray[info.position])
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
            else ->{
                super.onOptionsItemSelected(item)
            }
        }

    }
}

