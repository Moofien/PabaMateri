package com.example.pabamateri.p_implisit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.CalendarContract
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.pabamateri.R

class ImplisitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_implisit)

        // --- Inisialisasi View ---
        val btnKirimPesan: Button = findViewById(R.id.btnKirimPesan)
        val btnSetAlarm: Button = findViewById(R.id.btnSetAlarm)
        val btnSetTimer: Button = findViewById(R.id.btnSetTimer)
        val btnOpenURL: Button = findViewById(R.id.btnOpenURL)
        val tvAlamat: EditText = findViewById(R.id.tvAlamat)
        val btnSetEvent: Button = findViewById(R.id.btnSetEvent)
        val btnGetPhoto: Button = findViewById(R.id.btnGetPhoto)
        val ivHasil: ImageView = findViewById(R.id.ivHasil)

        // --- 1. Kirim Pesan (Sesuai PDF Topik 1) ---
        btnKirimPesan.setOnClickListener {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra("address", "08112345")
                putExtra("sms_body", "ISI SMS")
                type = "text/plain"
            }
            // Menggunakan resolveActivity sesuai PDF
            if (sendIntent.resolveActivity(packageManager) != null) {
                startActivity(sendIntent)
            } else {
                // Fallback jika resolveActivity gagal (biasanya di Emulator/Android 11+)
                try {
                    startActivity(Intent.createChooser(sendIntent, "Pilih Aplikasi Pesan"))
                } catch (e: Exception) {
                    Toast.makeText(this, "Aplikasi pesan tidak ditemukan", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // --- 2. Set Alarm (Sesuai PDF Topik 2) ---
        btnSetAlarm.setOnClickListener {
            val alarmIntent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
                putExtra(AlarmClock.EXTRA_MESSAGE, "COBA ALARM")
                putExtra(AlarmClock.EXTRA_HOUR, 11)
                putExtra(AlarmClock.EXTRA_MINUTES, 40)
                putExtra(AlarmClock.EXTRA_SKIP_UI, true)
            }
            if (alarmIntent.resolveActivity(packageManager) != null) {
                startActivity(alarmIntent)
            } else {
                startActivity(alarmIntent) // Paksa jalankan meski resolve null (untuk emulator)
            }
        }

        // --- 3. Set Timer (Sesuai PDF Topik 2) ---
        btnSetTimer.setOnClickListener {
            val timerIntent = Intent(AlarmClock.ACTION_SET_TIMER).apply {
                putExtra(AlarmClock.EXTRA_MESSAGE, "COBA TIMER")
                putExtra(AlarmClock.EXTRA_LENGTH, 40)
                putExtra(AlarmClock.EXTRA_SKIP_UI, true)
            }
            if (timerIntent.resolveActivity(packageManager) != null) {
                startActivity(timerIntent)
            } else {
                startActivity(timerIntent) // Paksa jalankan
            }
        }

        // --- 4. Buka Website (Sesuai PDF Topik 3) ---
        btnOpenURL.setOnClickListener {
            val urlText = tvAlamat.text.toString()
            val finalUrl = if (!urlText.startsWith("http")) "http://$urlText" else urlText

            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(finalUrl))

            if (webIntent.resolveActivity(packageManager) != null) {
                startActivity(webIntent)
            } else {
                try {
                    startActivity(webIntent)
                } catch (e: Exception) {
                    Toast.makeText(this, "Browser tidak ditemukan", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // --- 5. Set Event Calendar (Sesuai PDF Topik 4) ---
        btnSetEvent.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->

                    val selectedDateTime = Calendar.getInstance()
                    selectedDateTime.set(selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute)

                    val eventIntent = Intent(Intent.ACTION_INSERT).apply {
                        data = CalendarContract.Events.CONTENT_URI
                        putExtra(CalendarContract.Events.TITLE, "Meeting")
                        putExtra(CalendarContract.Events.EVENT_LOCATION, "Kantor")
                        putExtra(CalendarContract.Events.DESCRIPTION, "Deskripsi Meeting")
                        putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, selectedDateTime.timeInMillis)
                        putExtra(CalendarContract.EXTRA_EVENT_END_TIME, selectedDateTime.timeInMillis + 3600000) // +1 Jam
                    }
                    startActivity(eventIntent)

                }, 12, 0, true)
                timePickerDialog.show()
            }, year, month, day)
            datePickerDialog.show()
        }

        // --- 6. Kamera (Sesuai PDF Topik 5) ---
        val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            if (bitmap != null) {
                ivHasil.setImageBitmap(bitmap)
            }
        }

        btnGetPhoto.setOnClickListener {
            cameraLauncher.launch(null)
        }
    }
}