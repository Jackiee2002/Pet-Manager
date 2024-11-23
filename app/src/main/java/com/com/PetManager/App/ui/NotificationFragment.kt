package com.kroger.classapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class NotificationFragment : Fragment() {

    private lateinit var titleEditText: TextInputEditText
    private lateinit var messageEditText: TextInputEditText
    private lateinit var datePicker: DatePicker
    private lateinit var timePicker: TimePicker
    private lateinit var submitButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.notification_fragment, container, false)

        titleEditText = view.findViewById(R.id.titleET)
        messageEditText = view.findViewById(R.id.messageET)
        datePicker = view.findViewById(R.id.datePicker)
        timePicker = view.findViewById(R.id.timePicker)
        submitButton = view.findViewById(R.id.submitButton)

        submitButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !canScheduleExactAlarms()) {
                showPermissionDialog()
            } else {
                scheduleNotification()
            }
        }

        return view
    }

    private fun canScheduleExactAlarms(): Boolean {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.S || alarmManager?.canScheduleExactAlarms() == true
    }

    private fun showPermissionDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Permission Required")
            .setMessage("This app requires permission to schedule exact alarms. Please grant this permission in settings.")
            .setPositiveButton("Settings") { _, _ ->
                startActivity(Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun scheduleNotification() {
        val title = titleEditText.text.toString()
        val message = messageEditText.text.toString()
        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, datePicker.year)
            set(Calendar.MONTH, datePicker.month)
            set(Calendar.DAY_OF_MONTH, datePicker.dayOfMonth)
            set(Calendar.HOUR_OF_DAY, timePicker.hour)
            set(Calendar.MINUTE, timePicker.minute)
        }
        val intent = Intent(context, Notification::class.java).apply {
            putExtra("titleExtra", title)
            putExtra("messageExtra", message)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }
}
