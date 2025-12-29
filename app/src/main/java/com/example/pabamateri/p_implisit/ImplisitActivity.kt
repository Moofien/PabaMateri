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

        val btnKirimPesan: Button = findViewById(R.id.btnKirimPesan)
        val btnSetAlarm: Button = findViewById(R.id.btnSetAlarm)
        val btnSetTimer: Button = findViewById(R.id.btnSetTimer)
        val btnOpenURL: Button = findViewById(R.id.btnOpenURL)
        val tvAlamat: EditText = findViewById(R.id.tvAlamat)
        val btnSetEvent: Button = findViewById(R.id.btnSetEvent)
        val btnGetPhoto: Button = findViewById(R.id.btnGetPhoto)
        val ivHasil: ImageView = findViewById(R.id.ivHasil)

        btnKirimPesan.setOnClickListener {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra("address", "08112345")
                putExtra("sms_body", "ISI SMS")
                type = "text/plain"
            }

            if (sendIntent.resolveActivity(packageManager) != null) {
                startActivity(sendIntent)
            } else {
                try {
                    startActivity(Intent.createChooser(sendIntent, "Pilih Aplikasi Pesan"))
                } catch (e: Exception) {
                    Toast.makeText(this, "Aplikasi pesan tidak ditemukan", Toast.LENGTH_SHORT).show()
                }
            }
        }

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
                startActivity(alarmIntent)
            }
        }

        btnSetTimer.setOnClickListener {
            val timerIntent = Intent(AlarmClock.ACTION_SET_TIMER).apply {
                putExtra(AlarmClock.EXTRA_MESSAGE, "COBA TIMER")
                putExtra(AlarmClock.EXTRA_LENGTH, 40)
                putExtra(AlarmClock.EXTRA_SKIP_UI, true)
            }
            if (timerIntent.resolveActivity(packageManager) != null) {
                startActivity(timerIntent)
            } else {
                startActivity(timerIntent)
            }
        }

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
                        putExtra(CalendarContract.EXTRA_EVENT_END_TIME, selectedDateTime.timeInMillis + 3600000)
                    }
                    startActivity(eventIntent)

                }, 12, 0, true)
                timePickerDialog.show()
            }, year, month, day)
            datePickerDialog.show()
        }

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