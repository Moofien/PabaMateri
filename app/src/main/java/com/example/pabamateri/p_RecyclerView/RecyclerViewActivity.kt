package com.example.pabamateri.p_RecyclerView

import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pabamateri.R
import kotlin.jvm.java

class RecyclerViewActivity : AppCompatActivity() {

    private lateinit var _nama: MutableList<String>
    private lateinit var _karakter: MutableList<String>
    private lateinit var _deskripsi: MutableList<String>
    private lateinit var _gambar: MutableList<String>
    private var arWayang = arrayListOf<dcWayang>()
    private lateinit var _rvWayang: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        _rvWayang = findViewById(R.id.rvWayang)
        SiapkanData()
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
        arWayang.clear()
        for (position in _nama.indices) {
            val data = dcWayang(
                _gambar[position],
                _nama[position],
                _karakter[position],
                _deskripsi[position]
            )
            arWayang.add(data)
        }
    }

    fun TampilkanData() {
        _rvWayang.layoutManager = LinearLayoutManager(this)

        val adapterWayang = adapterRecView(arWayang)
        _rvWayang.adapter = adapterWayang

        adapterWayang.setOnItemClickCallback(object : adapterRecView.OnItemClickCallback {
            override fun onItemClicked(data: dcWayang) {
                val intent = android.content.Intent(this@RecyclerViewActivity, detWayang::class.java)
                intent.putExtra("kirimData", data)
                startActivity(intent)
            }

            override fun delData(pos: Int) {
                AlertDialog.Builder(this@RecyclerViewActivity)
                    .setTitle("HAPUS DATA")
                    .setMessage("Apakah Benar Data " + _nama[pos] + " akan dihapus?")
                    .setPositiveButton("HAPUS") { dialog: DialogInterface, which: Int ->
                        _gambar.removeAt(pos)
                        _nama.removeAt(pos)
                        _deskripsi.removeAt(pos)
                        _karakter.removeAt(pos)
                        TambahData()
                        TampilkanData()
                    }
                    .setNegativeButton("BATAL") { dialog: DialogInterface, which: Int ->
                        Toast.makeText(
                            this@RecyclerViewActivity,
                            "Data Batal Dihapus",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    .show()
            }
        })
    }
}