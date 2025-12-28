package com.example.pabamateri.p_RecyclerView

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.example.pabamateri.R
import com.example.pabamateri.p_RecyclerView.dcWayang


class adapterRecView(private val listWayang: ArrayList<dcWayang>) :
    RecyclerView.Adapter<adapterRecView.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: dcWayang)
        fun delData(pos: Int)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val _namaWayang: TextView = itemView.findViewById(R.id.namaWayang)
        val _karakterWayang: TextView = itemView.findViewById(R.id.karakterWayang)
        val _deskripsiWayang: TextView = itemView.findViewById(R.id.deskripsiWayang)
        val _gambarWayang: ImageView = itemView.findViewById(R.id.gambarWayang)
        val _btnHapus: TextView = itemView.findViewById(R.id.btnHapus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listWayang.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val wayang = listWayang[position]
        holder._namaWayang.text = wayang.nama
        holder._karakterWayang.text = wayang.karakter
        holder._deskripsiWayang.text = wayang.deskripsi

        Log.d("TEST", wayang.foto)
        Picasso.get()
            .load(wayang.foto)
            .resize(100, 100)
            .into(holder._gambarWayang)

        holder._gambarWayang.setOnClickListener {
            onItemClickCallback.onItemClicked(listWayang[holder.adapterPosition])
        }

        holder._btnHapus.setOnClickListener {
            onItemClickCallback.delData(holder.adapterPosition)
        }
    }
}