package com.example.pabamateri.p_explisit

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pabamateri.R

class ExplisitActivity3 : AppCompatActivity() {
    companion object {
        const val dataDiterima = "extra_dataTerima"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explisit3)

        val data = intent.getStringExtra(com.example.pabamateri.p_explisit.ExplisitActivity3.Companion.dataDiterima)
        val _showData = findViewById<TextView>(R.id.showData)
        _showData.text = data?.toString()
    }
}