package com.revolino.crud_data_mhs

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.revolino.crud_data_mhs.adapter.NotesAdapter
import com.revolino.crud_data_mhs.databinding.ActivityMainBinding
import com.revolino.crud_data_mhs.helper.NoteDatabaseHelper
import com.revolino.crud_data_mhs.model.Note

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: NoteDatabaseHelper
    private lateinit var notesAdapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NoteDatabaseHelper(this)
        notesAdapter = NotesAdapter(db.getAllNotes(), this)

        binding.notesRecycleview.layoutManager = LinearLayoutManager(this)
        binding.notesRecycleview.adapter = notesAdapter

        binding.addButton.setOnClickListener {
            val intent = Intent(this,add_note::class.java)
            startActivity(intent)
            startActivity(intent)
        }

    }
    override fun onResume() {
        super.onResume()
        val notes = db.getAllNotes()
        Log.d("MainActivity", "Notes count: ${notes.size}")
        notesAdapter.refreshData(notes)
    }
}