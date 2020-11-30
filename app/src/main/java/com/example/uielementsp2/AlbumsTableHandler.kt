package com.example.uielementsp2

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.uielementsp2.models.Album
import com.example.uielementsp2.models.Song

class AlbumsTableHandler (var context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "albums_database"
        private val TABLE_NAME = "album"
        private val COL_ID = "id"
        private val COL_ALBUM_TITLE = "album_title"
        private val COL_RELEASE_DATE = "release_date"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query ="CREATE TABLE "+ TABLE_NAME + "("+COL_ID +" INTEGER PRIMARY KEY, "+COL_ALBUM_TITLE +" TEXT, "+COL_RELEASE_DATE +" TEXT)"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME)
        onCreate(db)
    }
    fun create(album: Album): Boolean{
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_ALBUM_TITLE, album.album_title)
        contentValues.put(COL_RELEASE_DATE, album.release_date)
        val result = database.insert(TABLE_NAME, null, contentValues)

        if(result == (0).toLong()){
            return false
        }
        return true
    }
    fun read(): MutableList<Album> {
        val albumList : MutableList<Album> = ArrayList<Album>()
        val query = "SELECT * FROM "+ TABLE_NAME
        val database = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = database.rawQuery(query, null)
        }catch (e: SQLException){
            database.execSQL(query)
            return albumList
        }
        var id : Int
        var album_title : String
        var release_date : String
        if(cursor.moveToFirst()){
            do{
                id = cursor.getInt(cursor.getColumnIndex(COL_ID))
                album_title = cursor.getString(cursor.getColumnIndex(COL_ALBUM_TITLE))
                release_date = cursor.getString(cursor.getColumnIndex(COL_RELEASE_DATE))

                val album = Album(id,album_title, release_date)
                albumList.add(album)
            }while(cursor.moveToNext())
        }
        return albumList
    }
    fun readOne(album_id : Int): Album {
        var perSong = Album(0, "","")
        val query = "SELECT * FROM $TABLE_NAME WHERE id=$album_id"
        val database = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = database.rawQuery(query, null)
        }catch (e:SQLException){
            database.execSQL(query)
            return perSong
        }
        var id : Int
        var album_title : String
        var release_date : String
        if(cursor.moveToFirst()){

            id = cursor.getInt(cursor.getColumnIndex(COL_ID))
            album_title = cursor.getString(cursor.getColumnIndex(COL_ALBUM_TITLE))
            release_date = cursor.getString(cursor.getColumnIndex(COL_RELEASE_DATE))
            perSong = Album(id,album_title, release_date)

        }
        return perSong
    }
    fun title(): MutableList<String> {
        val albumList : MutableList<String> = ArrayList<String>()
        val query = "SELECT * FROM "+ TABLE_NAME
        val database = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = database.rawQuery(query, null)
        }catch (e: SQLException){
            database.execSQL(query)
            return albumList
        }
        var album_title : String
        if(cursor.moveToFirst()){
            do{
                album_title = cursor.getString(cursor.getColumnIndex(COL_ALBUM_TITLE))
                albumList.add(album_title)
            }while(cursor.moveToNext())
        }
        return albumList
    }
    fun update (album: Album):Boolean{
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_ALBUM_TITLE, album.album_title)
        contentValues.put(COL_RELEASE_DATE, album.release_date)
        val result = database.update(TABLE_NAME, contentValues, "id="+album.id, null)

        if(result == 0){
            return false
        }
        return true

    }

}