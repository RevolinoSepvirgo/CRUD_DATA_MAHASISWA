package com.revolino.crud_data_mhs.helper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.revolino.crud_data_mhs.model.Note

class NoteDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "notesapp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allnotes"
        private const val COLUMN_ID = "id"
        private const val COLUMN_Name = "Name"
        private const val COLUMN_Nim = "Nim"
        private const val COLUMN_Jurusan = "Jurusan"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_Name TEXT, $COLUMN_Nim TEXT, $COLUMN_Jurusan TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertNote(note: Note) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_Name, note.Nama)
            put(COLUMN_Nim, note.Nim)
            put(COLUMN_Jurusan, note.Jurusan)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }
    fun getAllNotes(): List<Note> {
        val noteList = mutableListOf<Note>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val Nama = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_Name))
            val Nim = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_Nim))
            val Jurusan = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_Jurusan))

            val note = Note(id, Nama, Nim, Jurusan)
            noteList.add(note)


        }
        cursor.close()
        db.close()
        return noteList

    }
    fun deleteNote(noteId: Int) {
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(noteId.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }
    fun updateNote(note: Note) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_Name, note.Nama)
            put(COLUMN_Nim, note.Nim)
            put(COLUMN_Jurusan, note.Jurusan)


        }

        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(note.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }
    fun getNoteByID(noteId: Int): Note {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $noteId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val Name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_Name))
        val Nim = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_Nim))
        val Jurusan = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_Jurusan))



        cursor.close()
        db.close()
        return Note(id, Name, Nim, Jurusan)
    }
}


