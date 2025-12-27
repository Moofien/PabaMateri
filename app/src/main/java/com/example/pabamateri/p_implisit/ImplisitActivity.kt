package com.example.pabamateri.p_implisit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.media.Image
import android.net.Uri
import android.provider.AlarmClock
import android.provider.CalendarContract
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.pabamateri.R

class ImplisitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_implisit)

        val _sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra("address", "09991918")
            putExtra("SMS_body" , "ISI sms")
            type = "text/plain"
        }
        if (_sendIntent.resolveActivity(packageManager) != null) {
            startActivity(Intent.createChooser(_sendIntent, "Pilih App"))
        }

        val _alarmIntent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, "COBA ALARM")
            putExtra(AlarmClock.EXTRA_HOUR, 11)
            putExtra(AlarmClock.EXTRA_MINUTES, 40)
            putExtra(AlarmClock.EXTRA_SKIP_UI, true)
        }
        startActivity(_alarmIntent)

        val _timerIntent: Intent = Intent(AlarmClock.ACTION_SET_TIMER).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, "COBA TIMER")
            putExtra(AlarmClock.EXTRA_LENGTH, 40)
            putExtra(AlarmClock.EXTRA_SKIP_UI, true)
        }
        startActivity(_timerIntent)

        val tvAlamat: EditText = findViewById(R.id.tvAlamat)
        val btnOpenURL: Button = findViewById(R.id.btnOpenURL)

        val _webIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("http://"+tvAlamat.text.toString())
        )

        if (_webIntent.resolveActivity(packageManager) != null) {
            startActivity(_webIntent)
        } else {
            Toast.makeText(
                this,
                "Tidak ada app browser ditemukan",
                Toast.LENGTH_LONG
            )
        }

        val btnSetEvent: Button = findViewById(R.id.btnSetEvent)

        btnSetEvent.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(selectedYear, selectedMonth, selectedDay)

                    val timePickerDialog = TimePickerDialog(
                        this,
                        { _, selectedHour, selectedMinute ->
                            val selectedDateTime = Calendar.getInstance()
                            selectedDateTime.set(selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute)

                            val _eventIntent = Intent(Intent.ACTION_INSERT).apply {
                                data = CalendarContract.Events.CONTENT_URI
                                putExtra(CalendarContract.Events.TITLE, "Meeting")
                                putExtra(CalendarContract.Events.EVENT_LOCATION, "Kantor")
                                putExtra(CalendarContract.Events.DESCRIPTION, "Deskripsi Meeting")
                                putExtra(CalendarContract.Events.ALL_DAY, false)

                                val startTime = selectedDateTime.clone() as Calendar
                                val endTime = selectedDateTime.clone() as Calendar
                                endTime.add(Calendar.HOUR_OF_DAY, 1)

                                putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime.timeInMillis)
                                putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.timeInMillis)
                            }

                            if (_eventIntent.resolveActivity(packageManager) != null) {
                                startActivity(_eventIntent)
                            }
                        },
                        hour, minute, true
                    )
                    timePickerDialog.show()
                },
                year, month, day
            )
            datePickerDialog.show()
        }

        val _btnGetPhoto: Button = findViewById(R.id.btnGetPhoto)
        val _ivHasil: ImageView = findViewById(R.id.ivHasil)

        val cameraLauncher =registerForActivityResult(
            ActivityResultContracts.TakePicturePreview()
        ) { bitmap ->
            if (bitmap != null) {
                _ivHasil.setImageBitmap(bitmap)
            }
        }
        _btnGetPhoto.setOnClickListener {
            cameraLauncher.launch(null)
        }

    }
}

