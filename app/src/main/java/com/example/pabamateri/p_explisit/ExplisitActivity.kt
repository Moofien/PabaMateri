package com.example.pabamateri.p_explisit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.pabamateri.R

class ExplisitActivity : AppCompatActivity() {
    override fun onStart() {
        super.onStart()
        Log.d("AppSaya","onStartBerjalan")
    }

    override fun onPause() {
        super.onPause()
        Log.d("AppSaya","onPauseBerjalan")
    }

    override fun onStop() {
        super.onStop()
        Log.d("AppSaya","onStopBerjalan")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("AppSaya","onDestroyBerjalan")
    }

    private lateinit var _returnHasil : TextView

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
            result ->
        if(result.resultCode == Activity.RESULT_OK && result.data != null){
            val selectedItem = result.data?.getStringExtra(
                ExplisitActivity5.SelectedItem
            )
            _returnHasil.text = selectedItem
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explisit)

        var _btnExplisit1 = findViewById<Button>(R.id.btnExplisit1)
        val _btnExplisit2 = findViewById<Button>(R.id.btnExplisit2)
        val _btnExplisit3 = findViewById<Button>(R.id.btnExplisit3)
        val _btnExplisit4 = findViewById<Button>(R.id.btnExplisit4)
        val _dataKirim = findViewById<EditText>(R.id.dataKirim)
        val isiPegawai : ArrayList<Pegawai> = arrayListOf()

        _btnExplisit1.setOnClickListener {
            val intent = Intent(
                this@ExplisitActivity,
                ExplisitActivity2::class.java
            )
            startActivity(intent)
        }

        _btnExplisit2.setOnClickListener {
            val intentWithData = Intent(
                this@ExplisitActivity,
                ExplisitActivity3::class.java
            ).apply {
                putExtra(ExplisitActivity3.dataDiterima, _dataKirim.text.toString())
            }
            startActivity(intentWithData)
        }

        isiPegawai.add(Pegawai(1,"Test","Test"))
        isiPegawai.add(Pegawai(2, "Test2", "Market"))

        _btnExplisit3.setOnClickListener {
            val intentWithData = Intent(
                this@ExplisitActivity,
                ExplisitActivity4::class.java
            ).apply {
                putExtra(ExplisitActivity4.dataPegawai, isiPegawai)
            }
            startActivity(intentWithData)
        }

        _returnHasil = findViewById(R.id.returnHasil)


        _btnExplisit4.setOnClickListener {
            val intentWithResult = Intent(
                this@ExplisitActivity,
                ExplisitActivity5::class.java)
            resultLauncher.launch(intentWithResult)
        }
        Log.d("AppSaya","onCreateBerjalan");
    }

}