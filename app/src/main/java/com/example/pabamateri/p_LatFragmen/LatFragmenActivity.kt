package com.example.pabamateri.p_LatFragmen

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.pabamateri.R

class LatFragmenActivity : AppCompatActivity() {
    lateinit var etAngka1: EditText
    lateinit var etAngka2: EditText
    private lateinit var btnHitung: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lat_fragmen)

        etAngka1 = findViewById(R.id.etAngka1)
        etAngka2 = findViewById(R.id.etAngka2)
        btnHitung = findViewById(R.id.btnHitung)

        replaceFragment(OperasiFragment())

        btnHitung.setOnClickListener {
            val input1 = etAngka1.text.toString()
            val input2 = etAngka2.text.toString()

            // TAMBAHKAN VALIDASI - jika kosong, beri pesan error
            if (input1.isEmpty() || input2.isEmpty()) {
                // Tampilkan toast atau dialog error
                Toast.makeText(this, "Mohon isi kedua angka terlebih dahulu!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val angka1 = input1.toDoubleOrNull() ?: 0.0
            val angka2 = input2.toDoubleOrNull() ?: 0.0
            val hasil = angka1 + angka2

            val fragment = HasilPenjumlahanFragment().apply {
                arguments = Bundle().apply {
                    putDouble("ANGKA1", angka1)
                    putDouble("ANGKA2", angka2)
                    putDouble("HASIL", hasil)
                }
            }
            replaceFragment(fragment)
        }
    }
    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}