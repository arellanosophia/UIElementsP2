package com.example.uielementsp2

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
class AlbumDetailsActivity : AppCompatActivity() {

    lateinit var adapter : ArrayAdapter<String>
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "i.apps.notifications"
    private val description = "Test notification"
    @SuppressLint("WrongViewCast")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_details)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val albumTitleTextView = findViewById<TextView>(R.id.albumTitleTextView)
        val albumImageView= findViewById<ImageView>(R.id.albumImageView)

        val position = intent.extras!!.getString("position")


        if (position.equals("Nothing Happens")) {
            albumTitleTextView.text = position
            albumImageView.setImageResource(R.drawable.nothing_happens)
            selectedSong = arrayListOf("Are You Bored Yet?" , "Scrawny" , "Remember When" , "Treacherous Doctor" , "Do Not Wait" , "Ice Cold Pool",
                    "Are You Bored Yet?" , "Scrawny" , "Remember When" , "Treacherous Doctor" , "Do Not Wait" , "Ice Cold Pool" ,
            "Only Friend" , "Sidelines" , "Worlds Apart" , "What You Like" , "I'm Full")
        } else if (position.equals("Remote")){
            albumTitleTextView.text = position
            albumImageView.setImageResource(R.drawable.remote)
            selectedSong = arrayListOf("Wish Me Luck","Coastlines", "Virtual Aerobics","Dig What You Dug","Nobody Gets Me(Like You)","Talk Like That")
        } else if (position.equals("Spring")){
            albumTitleTextView.text = position
            albumImageView.setImageResource(R.drawable.spring)
            selectedSong = arrayListOf("Pictures of Girls","These Days","1980s Horror Film","Ground","Let The Sun In", "It's Only Right")
        }else {
            val getSong = intent.extras!!.getString("song")
            val album_title = intent.extras!!.getString("album_title")
            albumTitleTextView.text = album_title
            albumImageView.setImageResource(R.drawable.uc)
            selectedSong.add(getSong.toString())
            adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, selectedSong)
        }

        adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, selectedSong)
        var albumDetailListView = findViewById<ListView>(R.id.albumDetailListView)
        albumDetailListView.adapter = adapter
        registerForContextMenu(albumDetailListView)
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
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.remove_option, menu)
    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.remove_song ->  {
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setMessage("Do you really want to delete this?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", { _ , _ ->


                            val toast:Toast = Toast.makeText(applicationContext, "Song removed from album.", Toast.LENGTH_SHORT)
                            toast.show()

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                notificationChannel = NotificationChannel(
                                        channelId , description , NotificationManager.IMPORTANCE_HIGH)
                                notificationChannel.enableLights(true)
                                notificationChannel.lightColor = Color.BLUE
                                notificationChannel.enableVibration(true)
                                notificationManager.createNotificationChannel(notificationChannel)

                                builder = Notification.Builder(this , channelId)
                                        .setContentTitle("Song Removed")
                                        .setContentText("You deleted a song from the album.")
                                        .setSmallIcon(R.drawable.ic_launcher_background)

                            }else{
                                builder = Notification.Builder(this)
                                        .setContentTitle("Song Removed")
                                        .setContentText("You deleted a song from the album.")
                                        .setSmallIcon(R.drawable.ic_launcher_background)
                            }
                            notificationManager.notify(1234, builder.build())
                            adapter.notifyDataSetChanged() // REMOVE SONG AFTER CLICKING YES
                        }).setNegativeButton("No", { dialog , _ ->
                            dialog.cancel()
                        })

                val alert = dialogBuilder.create()
                alert.setTitle("Notification Manager")
                alert.show()

                val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
                selectedSong.removeAt(info.position)
                true
            }
            else -> super.onContextItemSelected(item)
        }

    }
}
