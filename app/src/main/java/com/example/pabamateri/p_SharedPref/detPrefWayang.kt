package com.example.pabamateri.p_SharedPref


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.example.pabamateri.R
import com.example.pabamateri.p_RecyclerView.dcWayang

class detPrefWayang : AppCompatActivity() {
    private lateinit var _detFotoWayang: ImageView
    private lateinit var _detNamaWayang: TextView
    private lateinit var _detDetailWayang: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_det_pref_wayang)

        _detFotoWayang = findViewById(R.id.detFotoWayang)
        _detNamaWayang = findViewById(R.id.detNamaWayang)
        _detDetailWayang = findViewById(R.id.detDetailWayang)

        val dataIntent = intent.getParcelableExtra<dcPrefWayang>("kirimData")

        if (dataIntent != null) {
            Picasso.get()
                .load(dataIntent.foto)
                .into(_detFotoWayang)

            _detNamaWayang.text = dataIntent.nama
            _detDetailWayang.text = dataIntent.deskripsi
        }
    }
}