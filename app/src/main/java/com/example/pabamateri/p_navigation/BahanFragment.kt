package com.example.pabamateri.p_navigation

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pabamateri.R
import com.example.pabamateri.databinding.FragmentBahanBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class BahanFragment : Fragment() {

    private var _binding: FragmentBahanBinding? = null
    private val binding get() = _binding!!

    lateinit var sp: SharedPreferences
    val gson = Gson()
    var arBahan = arrayListOf<Bahan>()
    lateinit var adapter: AdapterBahan

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBahanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sp = requireActivity().getSharedPreferences("my_sp_cooking", Context.MODE_PRIVATE)

        loadData() // 1. Load data dari SP

        // 2. Jika kosong, isi 20 data dummy (Sesuai Soal)
        if (arBahan.isEmpty()) {
            for (i in 1..20) {
                arBahan.add(Bahan("Bahan Masakan $i", "Kategori Umum", "https://via.placeholder.com/150"))
            }
            saveData("dt_bahan", arBahan)
        }

        // 3. Setup RecyclerView
        adapter = AdapterBahan(arBahan, isCart = false) { item, aksi ->
            when (aksi) {
                "DELETE" -> {
                    arBahan.remove(item)
                    saveData("dt_bahan", arBahan)
                    adapter.notifyDataSetChanged()
                    Toast.makeText(context, "${item.nama} dihapus", Toast.LENGTH_SHORT).show()
                }
                "EDIT" -> showDialogInput(item) // Mode Edit
                "CART_OR_CHECK" -> addToCart(item) // Masuk Keranjang
            }
        }
        binding.rvBahan.layoutManager = LinearLayoutManager(context)
        binding.rvBahan.adapter = adapter

        // 4. FAB Tambah Data
        binding.fabAddBahan.setOnClickListener {
            showDialogInput(null) // Mode Tambah (null)
        }
    }

    // Fungsi Menampilkan Dialog (Bisa untuk Tambah / Edit)
    private fun showDialogInput(bahanEdit: Bahan?) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_bahan, null)
        val etNama = dialogView.findViewById<EditText>(R.id.etNamaBahan)
        val etKategori = dialogView.findViewById<EditText>(R.id.etKategoriBahan)
        val etUrl = dialogView.findViewById<EditText>(R.id.etUrlGambar)
        val btnSimpan = dialogView.findViewById<Button>(R.id.btnSimpanBahan)

        // Jika Edit, isi form dengan data lama
        if (bahanEdit != null) {
            etNama.setText(bahanEdit.nama)
            etKategori.setText(bahanEdit.kategori)
            etUrl.setText(bahanEdit.imageUrl)
        }

        val dialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .setTitle(if (bahanEdit == null) "Tambah Bahan" else "Edit Bahan")
            .create()

        btnSimpan.setOnClickListener {
            val nama = etNama.text.toString()
            val kategori = etKategori.text.toString()
            val url = etUrl.text.toString()

            if (nama.isNotEmpty() && kategori.isNotEmpty()) {
                if (bahanEdit == null) {
                    // Tambah Baru
                    arBahan.add(Bahan(nama, kategori, url))
                } else {
                    // Update Lama
                    bahanEdit.nama = nama
                    bahanEdit.kategori = kategori
                    bahanEdit.imageUrl = url
                }
                saveData("dt_bahan", arBahan)
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            } else {
                Toast.makeText(context, "Isi data dengan lengkap!", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.show()
    }

    private fun addToCart(item: Bahan) {
        // Ambil data keranjang lama
        val jsonCart = sp.getString("dt_cart", null)
        val listCart = if (jsonCart != null) {
            gson.fromJson<ArrayList<Bahan>>(jsonCart, object : TypeToken<ArrayList<Bahan>>() {}.type)
        } else {
            arrayListOf()
        }

        listCart.add(item) // Tambah item
        saveData("dt_cart", listCart) // Simpan
        Toast.makeText(context, "Masuk keranjang!", Toast.LENGTH_SHORT).show()
    }

    private fun saveData(key: String, list: ArrayList<Bahan>) {
        val editor = sp.edit()
        editor.putString(key, gson.toJson(list))
        editor.apply()
    }

    private fun loadData() {
        val json = sp.getString("dt_bahan", null)
        if (json != null) {
            arBahan = gson.fromJson(json, object : TypeToken<ArrayList<Bahan>>() {}.type)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}