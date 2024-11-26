package com.revolino.crud_data_mhs

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.revolino.crud_data_mhs.databinding.ActivityAddNoteBinding
import com.revolino.crud_data_mhs.helper.NoteDatabaseHelper
import com.revolino.crud_data_mhs.model.Note

class add_note : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var db: NoteDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_note)

        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = NoteDatabaseHelper(this)

        binding.saveButton.setOnClickListener {
            val Nama = binding.etNama.text.toString()
            val Nim = binding.etNim.text.toString()
            val Jurusan = binding.etJurusan.text.toString()
            val note = Note(0, Nama, Nim, Jurusan)
            db.insertNote(note)
            finish()
            Toast.makeText(this, "Catatan disimpan", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Tutup activity ini agar tidak bisa kembali dengan tombol back
        }

    }
}