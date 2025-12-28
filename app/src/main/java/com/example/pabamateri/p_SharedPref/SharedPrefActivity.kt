package com.example.pabamateri.p_SharedPref

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.edit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pabamateri.R
import com.example.pabamateri.p_SharedPref.detPrefWayang
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


import kotlin.jvm.java

class SharedPrefActivity : AppCompatActivity() {

    private var _nama: MutableList<String> = emptyList<String>().toMutableList()
    private var _karakter: MutableList<String> = emptyList<String>().toMutableList()
    private var _deskripsi: MutableList<String> = emptyList<String>().toMutableList()
    private var _gambar: MutableList<String> = emptyList<String>().toMutableList()
    private var arWayang = arrayListOf<dcPrefWayang>()
    private lateinit var _rvWayang: RecyclerView
    private lateinit var sp: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_pref)
        _rvWayang = findViewById(R.id.rvWayang)

        sp = getSharedPreferences("dataSP", MODE_PRIVATE)

        val gson = Gson()
        val isiSP = sp.getString("spWayang", null)
        if (isiSP != null) {
            val type = object : TypeToken<ArrayList<dcPrefWayang>>() {}.type
            arWayang = gson.fromJson(isiSP, type)
        }
        if (arWayang.size == 0) {
            SiapkanData()
        } else {
            arWayang.forEach {
                _nama.add(it.nama)
                _gambar.add(it.foto)
                _deskripsi.add(it.deskripsi)
                _karakter.add(it.karakter)
            }
            arWayang.clear()
        }

        TambahData()
        TampilkanData()
    }
    fun SiapkanData() {
        _nama = resources.getStringArray(R.array.namaWayang).toMutableList()
        _deskripsi = resources.getStringArray(R.array.deskripsiWayang).toMutableList()
        _karakter = resources.getStringArray(R.array.karakterUtamaWayang).toMutableList()
        _gambar = resources.getStringArray(R.array.gambarWayang).toMutableList()
    }

    fun TambahData() {
        val gson = Gson()
        sp.edit {
            arWayang.clear()
            for (position in _nama.indices) {
                val data = dcPrefWayang(
                    _gambar[position],
                    _nama[position],
                    _karakter[position],
                    _deskripsi[position]
                )
                arWayang.add(data)
            }

            val json = gson.toJson(arWayang)
            putString("spWayang", json)
        }
    }

    fun TampilkanData() {
        _rvWayang.layoutManager = LinearLayoutManager(this)
        val adapterWayang = adapterRecView(arWayang)
        _rvWayang.adapter = adapterWayang

        adapterWayang.setOnItemClickCallback(object :
            adapterRecView.OnItemClickCallback {

            override fun onItemClicked(data: dcPrefWayang) {
                val intent = Intent(this@SharedPrefActivity, detPrefWayang::class.java)
                intent.putExtra("kirimData", data)
                startActivity(intent)
            }

            override fun delData(pos: Int) {
                AlertDialog.Builder(this@SharedPrefActivity)
                    .setTitle("HAPUS DATA")
                    .setMessage("Apakah Benar Data " + _nama[pos] + " akan dihapus ?")
                    .setPositiveButton("HAPUS") { dialog, which ->
                        _gambar.removeAt(pos)
                        _nama.removeAt(pos)
                        _deskripsi.removeAt(pos)
                        _karakter.removeAt(pos)

                        TambahData()
                        TampilkanData()
                    }
                    .setNegativeButton("BATAL") { dialog, which ->
                        Toast.makeText(
                            this@SharedPrefActivity,
                            "Data Batal Dihapus",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    .show()
            }
        })
    }
}