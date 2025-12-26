package com.example.pabamateri.p_explisit

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pabamateri.R
import kotlin.collections.get
import kotlin.toString

class ExplisitActivity4 : AppCompatActivity() {
    companion object {
        val dataPegawai = "kirimDataPegawai"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explisit4)

        val intentPegawai = intent.getParcelableArrayListExtra<Pegawai>(
            com.example.pabamateri.p_explisit.ExplisitActivity4.Companion.dataPegawai,
            Pegawai::class.java
        )

        val isiText = "NIP: ${intentPegawai!![0].NIP.toString()}, " +
                "\nNama : ${intentPegawai!![0].Nama.toString()}, "+
                "\nDept : ${intentPegawai!![0].Dept.toString()}" +
                "\n" +
                "\nNIP: ${intentPegawai?.get(1)?.NIP.toString()}, " +
                "\nNama : ${intentPegawai?.get(1)?.Nama.toString()}, "+
                "\nDept : ${intentPegawai?.get(1)?.Dept.toString()}"
        val _showDataPegawai = findViewById<TextView>(R.id.showDataPegawai)
        _showDataPegawai.text = isiText
    }
}