package com.example.pabamateri.p_Room // <--- Package sudah disesuaikan

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.pabamateri.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import com.example.pabamateri.p_Room.database.Note
import com.example.pabamateri.p_Room.database.NoteRoomDatabase
import com.example.pabamateri.p_Room.Helper.DateHelper.getCurrentDate
import kotlinx.coroutines.withContext

class TambahData : AppCompatActivity() {
    var iID: Int = 0
    var iAddEdit: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_data)

        val DB :NoteRoomDatabase = NoteRoomDatabase.getDatabase(this)
        val tanggal : String = getCurrentDate()

        val _etJudul = findViewById<EditText>(R.id.etJudul)
        val _etDeskripsi = findViewById<EditText>(R.id.etDeskripsi)
        val _btnTambah = findViewById<Button>(R.id.btnTambah)
        val _btnUpdate = findViewById<Button>(R.id.btnUpdate)


        iID = intent.getIntExtra("noteId", 0)
        iAddEdit = intent.getIntExtra("addEdit", 0)

        if (iAddEdit == 0) {
            _btnTambah.visibility = View.VISIBLE
            _btnUpdate.visibility = View.GONE
            _etJudul.isEnabled = true
        } else {
            _btnTambah.visibility = View.GONE
            _btnUpdate.visibility = View.VISIBLE
            _etJudul.isEnabled = false

            CoroutineScope(Dispatchers.IO).async {
                val noteItem = DB.funnoteDao().getNote(iID)
                withContext(Dispatchers.Main) {
                    _etJudul.setText(noteItem.judul)
                    _etDeskripsi.setText(noteItem.deskripsi)
                }
            }
        }

        _btnTambah.setOnClickListener {
            CoroutineScope(Dispatchers.IO).async {
                DB.funnoteDao().insert(
                    Note(
                        0,
                        _etJudul.text.toString(),
                        _etDeskripsi.text.toString(),
                        tanggal
                    )
                )
                finish()
            }
        }

        _btnUpdate.setOnClickListener {
            CoroutineScope(Dispatchers.IO).async {
                DB.funnoteDao().update(
                    _etJudul.text.toString(),
                    _etDeskripsi.text.toString(),
                    iID
                )
                finish()
            }
        }
    }
}