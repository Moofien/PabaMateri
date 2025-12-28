package com.example.pabamateri.p_Room

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pabamateri.p_Room.database.Note
import com.example.pabamateri.R
import com.example.pabamateri.p_Room.TambahData


class adapterNote(private val listNote: MutableList<Note>) :
    RecyclerView.Adapter<adapterNote.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun delData(dtnote: Note)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvJudul: TextView = itemView.findViewById(R.id.tvJudul)
        var tvDeskripsi: TextView = itemView.findViewById(R.id.tvDeskripsi)
        var tvTanggal: TextView = itemView.findViewById(R.id.tvTanggal)
        var btnEdit: ImageButton = itemView.findViewById(R.id.btnEdit)
        var btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listNote.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var note: Note = listNote[position]

        holder.tvJudul.setText(note.judul)
        holder.tvDeskripsi.setText(note.deskripsi)
        holder.tvTanggal.setText(note.tanggal)

        holder.btnEdit.setOnClickListener {
            val intent = Intent(it.context, TambahData::class.java)
            intent.putExtra("noteId", note.id)
            intent.putExtra("addEdit", 1)
            it.context.startActivity(intent)
        }

        holder.btnDelete.setOnClickListener {
            onItemClickCallback.delData(note)
        }
    }

    fun isiData(list: List<Note>) {
        listNote.clear()
        listNote.addAll(list)
        notifyDataSetChanged()
    }
}