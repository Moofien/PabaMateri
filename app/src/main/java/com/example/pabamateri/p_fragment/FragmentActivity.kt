package com.example.pabamateri.p_fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.pabamateri.R

class FragmentActivity : AppCompatActivity() {
    fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        var _btn1 = findViewById<Button>(R.id.btnFragmentSatu)
        var _btn2 = findViewById<Button>(R.id.btnFragmentDua)

        if (savedInstanceState == null) {
            replaceFragment(fSatu())
        }
        _btn1.setOnClickListener {
            replaceFragment(fSatu())
        }
        _btn2.setOnClickListener {
            replaceFragment(fDua())
        }
    }
}