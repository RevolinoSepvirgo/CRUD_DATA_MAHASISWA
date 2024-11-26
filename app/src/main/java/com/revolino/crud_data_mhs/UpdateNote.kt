package com.revolino.crud_data_mhs

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.revolino.crud_data_mhs.databinding.ActivityUpdateNoteBinding
import com.revolino.crud_data_mhs.helper.NoteDatabaseHelper
import com.revolino.crud_data_mhs.model.Note

class UpdateNote : AppCompatActivity() {
        private lateinit var binding: ActivityUpdateNoteBinding
        private lateinit var db: NoteDatabaseHelper
        private var noteId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update_note)

        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NoteDatabaseHelper(this)

        noteId = intent.getIntExtra("note_id", -1)
        if (noteId == -1) {
            finish()
            return
        }
        val note = db.getNoteByID(noteId)
        binding.updateTitleNamaText.setText(note.Nama)
        binding.updateContentNimText.setText(note.Nim)
        binding.updateContentJurusanText.setText(note.Jurusan)

        binding.saveEdit.setOnClickListener {
            val newName = binding.updateTitleNamaText.text.toString()
            val newNim = binding.updateContentNimText.text.toString()
            val newJurusan = binding.updateContentJurusanText.text.toString()

            val updatedNote = Note(id = noteId, Nama = newName, Nim = newNim, Jurusan = newJurusan)
            db.updateNote(updatedNote)
            finish()


            Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()
            finish()
        } 

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}