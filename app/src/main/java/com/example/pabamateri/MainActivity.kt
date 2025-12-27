package com.example.pabamateri

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.pabamateri.p_LatFragmen.LatFragmenActivity
import com.example.pabamateri.p_cons1.Cons1Activity
import com.example.pabamateri.p_cons2.Cons2Activity
import com.example.pabamateri.p_explisit.ExplisitActivity
import com.example.pabamateri.p_fragment.FragmentActivity
import com.example.pabamateri.p_implisit.ImplisitActivity
import com.example.pabamateri.p_layout.LayoutActivity
import com.example.pabamateri.p_navigation.NavActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnP1 = findViewById<Button>(R.id.btn_layout)
        val btnP2 = findViewById<Button>(R.id.btn_cons1)
        val btnP3 = findViewById<Button>(R.id.btn_cons2)
        val btnP4 = findViewById<Button>(R.id.btn_exp)
        val btnP5 = findViewById<Button>(R.id.btn_imp)
        val btnP6 = findViewById<Button>(R.id.btn_frag)
        val btnP7 = findViewById<Button>(R.id.btn_fragLat)
        val btnP8 = findViewById<Button>(R.id.btn_nav)


        btnP1.setOnClickListener {
            val intent = Intent(this, LayoutActivity::class.java)
            startActivity(intent)
        }
        btnP2.setOnClickListener {
            val intent = Intent(this, Cons1Activity::class.java)
            startActivity(intent)
        }
        btnP3.setOnClickListener {
            val intent = Intent(this, Cons2Activity::class.java)
            startActivity(intent)
        }
        btnP4.setOnClickListener {
            val intent = Intent(this, ExplisitActivity::class.java)
            startActivity(intent)
        }
        btnP5.setOnClickListener {
            val intent = Intent(this, ImplisitActivity::class.java)
            startActivity(intent)
        }
        btnP6.setOnClickListener {
            val intent = Intent(this, FragmentActivity::class.java)
            startActivity(intent)
        }
        btnP7.setOnClickListener {
            val intent = Intent(this, LatFragmenActivity::class.java)
            startActivity(intent)
        }
        btnP8.setOnClickListener {
            val intent = Intent(this, NavActivity::class.java)
            startActivity(intent)
        }
    }
}