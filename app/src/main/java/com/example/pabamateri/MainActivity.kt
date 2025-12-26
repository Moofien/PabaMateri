package com.example.pabamateri

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pabamateri.p_cons1.Cons1Activity
import com.example.pabamateri.p_cons2.Cons2Activity
import com.example.pabamateri.p_explisit.ExplisitActivity
import com.example.pabamateri.p_layout.LayoutActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnP1 = findViewById<Button>(R.id.btn_layout)
        val btnP2 = findViewById<Button>(R.id.btn_cons1)
        val btnP3 = findViewById<Button>(R.id.btn_cons2)
        val btnP4 = findViewById<Button>(R.id.btn_exp)

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
    }
}