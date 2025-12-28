package com.example.pabamateri.p_Room

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pabamateri.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import com.example.pabamateri.p_Room.database.Note
import com.example.pabamateri.p_Room.database.NoteRoomDatabase

class RoomActivity : AppCompatActivity() {

    private lateinit var DB: NoteRoomDatabase
    private lateinit var adapterN: adapterNote
    private var arNote: MutableList<Note> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        DB = NoteRoomDatabase.getDatabase(this)
        val _fabAdd = findViewById<FloatingActionButton>(R.id.fabAdd)
        adapterN = adapterNote(arNote)

        val _rvNotes = findViewById<RecyclerView>(R.id.rvNotes)
        _rvNotes.layoutManager = LinearLayoutManager(this)
        _rvNotes.adapter = adapterN

        adapterN.setOnItemClickCallback(object : adapterNote.OnItemClickCallback {
            override fun delData(dtnote: Note) {
                CoroutineScope(Dispatchers.IO).async {
                    DB.funnoteDao().delete(dtnote)
                    val note = DB.funnoteDao().selectAll()
                    Log.d("data ROOM2", note.toString())

                    withContext(Dispatchers.Main) {
                        adapterN.isiData(note)
                    }
                }
            }
        })

        _fabAdd.setOnClickListener {
            startActivity(Intent(this, TambahData::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.Main).async {
            val note = DB.funnoteDao().selectAll()
            Log.d("data ROOM", note.toString())

            adapterN.isiData(note)
        }
    }
}