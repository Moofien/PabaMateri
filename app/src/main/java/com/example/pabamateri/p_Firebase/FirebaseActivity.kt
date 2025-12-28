package com.example.pabamateri.p_Firebase

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.pabamateri.R
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class FirebaseActivity : AppCompatActivity() {

    // Langkah 16: Inisialisasi Firestore
    val db = Firebase.firestore


    var data: MutableList<Map<String, String>> = ArrayList()
    lateinit var lvAdapter: SimpleAdapter

    // Langkah 18: Variabel UI
    lateinit var _etProvinsi: EditText
    lateinit var _etIbukota: EditText
    lateinit var _btnSimpan: Button
    lateinit var _lvData: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase)

        // Langkah 19: Inisialisasi View
        _etProvinsi = findViewById(R.id.etProvinsi)
        _etIbukota = findViewById(R.id.etIbukota)
        _btnSimpan = findViewById(R.id.btnSimpan)
        _lvData = findViewById(R.id.lvData)

        // Langkah 30: Setup SimpleAdapter
        lvAdapter = SimpleAdapter(
            this,
            data,
            android.R.layout.simple_list_item_2, // Menggunakan layout bawaan android 2 baris
            arrayOf("Pro", "Ibu"),
            intArrayOf(android.R.id.text1, android.R.id.text2)
        )
        _lvData.adapter = lvAdapter

        // Langkah 22: Tombol Simpan
        _btnSimpan.setOnClickListener {
            val provinsi = _etProvinsi.text.toString()
            val ibukota = _etIbukota.text.toString()

            if (provinsi.isNotBlank() && ibukota.isNotBlank()) {
                TambahData(db, provinsi, ibukota)
            } else {
                Toast.makeText(this, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }

        // Langkah 37: Hapus Data (Long Click)
        _lvData.setOnItemLongClickListener { parent, view, position, id ->
            // Ambil nama provinsi dari data yang diklik
            val namaPro = data[position]["Pro"].toString()

            // Tampilkan Dialog Konfirmasi (Optional tapi disarankan)
            val adb = AlertDialog.Builder(this)
            adb.setTitle("Hapus Data")
            adb.setMessage("Yakin menghapus $namaPro?")
            adb.setPositiveButton("Ya") { _, _ ->
                // Proses Hapus dari Firestore
                db.collection("tbProvinsi")
                    .document(namaPro)
                    .delete()
                    .addOnSuccessListener {
                        Log.d("Firebase", "$namaPro Berhasil di HAPUS")
                        readData(db) // Refresh data setelah hapus
                        Toast.makeText(this, "$namaPro dihapus", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Log.d("Firebase", it.message.toString())
                    }
            }
            adb.setNegativeButton("Batal", null)
            adb.show()

            true
        }

        // Langkah 26: Baca Data saat aplikasi pertama jalan
        readData(db)
    }

    // Langkah 35: Fungsi Tambah Data (Menggunakan .set() agar document ID = Nama Provinsi)
    fun TambahData(db: FirebaseFirestore, Provinsi: String, Ibukota: String) {
        val dataBaru = dtProvinsi(Provinsi, Ibukota)

        db.collection("tbProvinsi")
            .document(dataBaru.provinsi) // Gunakan nama provinsi sebagai ID Dokumen
            .set(dataBaru)
            .addOnSuccessListener {
                _etProvinsi.setText("")
                _etIbukota.setText("")
                Log.d("Firebase", "${dataBaru.provinsi} Berhasil ditambahkan")
                readData(db) // Refresh list setelah simpan
            }
            .addOnFailureListener {
                Log.d("Firebase", it.message.toString())
            }
    }

    // Langkah 25 & 29: Fungsi Baca Data (Read)
    fun readData(db: FirebaseFirestore) {
        db.collection("tbProvinsi").get()
            .addOnSuccessListener { result ->
                data.clear()
                for (document in result) {
                    val dt: MutableMap<String, String> = HashMap(2)
                    dt["Pro"] = document.data["provinsi"].toString()
                    dt["Ibu"] = document.data["ibukota"].toString()
                    data.add(dt)
                }
                lvAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Log.d("Firebase", it.message.toString())
            }
    }
}