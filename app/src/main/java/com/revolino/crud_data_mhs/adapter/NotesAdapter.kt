package com.revolino.crud_data_mhs.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.revolino.crud_data_mhs.R
import com.revolino.crud_data_mhs.UpdateNote
import com.revolino.crud_data_mhs.helper.NoteDatabaseHelper
import com.revolino.crud_data_mhs.model.Note

class NotesAdapter(private var notes: List<Note>, context: Context) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val db :NoteDatabaseHelper= NoteDatabaseHelper(context)

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNama = itemView.findViewById<TextView>(R.id.txtNama)
        val txtNim = itemView.findViewById<TextView>(R.id.txtNim)
        val txtJurusan = itemView.findViewById<TextView>(R.id.txtJurusan)
        val btnEdite :ImageView=itemView.findViewById(R.id.btnEdite)
        val btnDelete :ImageView=itemView.findViewById(R.id.btnDelete)




    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int = notes.size



    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.txtNama.text = note.Nama
        holder.txtNim.text = note.Nim
        holder.txtJurusan.text = note.Jurusan
        holder.btnDelete.setOnClickListener {
            AlertDialog.Builder(holder.itemView.context).apply {
                setTitle("confirmation")
                setMessage("do you want to continue?")
                setIcon(R.drawable.baseline_delete_24)

                setPositiveButton("yes"){
                        dialogInterface, i->
                    db.deleteNote(note.id)
                    refreshData(db.getAllNotes())
                    Toast.makeText(holder.itemView.context, "Note berhasil dihapus", Toast.LENGTH_SHORT).show()
                    dialogInterface.dismiss()
                }

                setNeutralButton("no"){
                        dialogInterface, i->
                    dialogInterface.dismiss()
                    Toast.makeText(holder.itemView.context, "Note tidak jadi dihapus", Toast.LENGTH_SHORT).show()
                }

            }.show()



        }
        holder.btnEdite.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Tombol edit ditekan", Toast.LENGTH_SHORT).show()


            val intent = Intent(holder.itemView.context, UpdateNote::class.java).apply {
                putExtra("note_id", note.id)
                putExtra("note_nama", note.Nama)
                putExtra("note_nim", note.Nim)
                putExtra("note_jurusan", note.Jurusan)




            }
            holder.itemView.context.startActivity(intent)
        }

    }
    fun refreshData(newNotes: List<Note>) {
        notes = newNotes
        notifyDataSetChanged()
    }


}
