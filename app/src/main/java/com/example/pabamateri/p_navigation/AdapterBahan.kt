package com.example.pabamateri.p_navigation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pabamateri.R

class AdapterBahan(
    private val listBahan: ArrayList<Bahan>,
    private val isCart: Boolean = false,
    private val onItemClick: (Bahan, String) -> Unit
) : RecyclerView.Adapter<AdapterBahan.ListViewHolder>() {

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvNama: TextView = itemView.findViewById(R.id.tvNamaBahan)
        var tvKategori: TextView = itemView.findViewById(R.id.tvKategori)
        var btnEdit: ImageButton = itemView.findViewById(R.id.btnEdit)
        var btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)
        var btnCart: ImageButton = itemView.findViewById(R.id.btnCart)
        var imgBahan: ImageView = itemView.findViewById(R.id.imgBahan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bahan, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listBahan[position]
        holder.tvNama.text = data.nama
        holder.tvKategori.text = data.kategori

        // load Gambar pakai Glide biar tidak crash kalau URL error
        Glide.with(holder.itemView.context)
            .load(data.imageUrl)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .into(holder.imgBahan)

        // logika tampilan keranjang dan bahan biasa
        if (isCart) {
            holder.btnEdit.visibility = View.GONE
            holder.btnCart.setImageResource(android.R.drawable.checkbox_on_background)
        } else {
            holder.btnEdit.visibility = View.VISIBLE
            holder.btnCart.setImageResource(android.R.drawable.ic_input_add)
        }

        holder.btnEdit.setOnClickListener { onItemClick(data, "EDIT") }
        holder.btnDelete.setOnClickListener { onItemClick(data, "DELETE") }
        holder.btnCart.setOnClickListener { onItemClick(data, "CART_OR_CHECK") }
    }

    override fun getItemCount(): Int = listBahan.size
}