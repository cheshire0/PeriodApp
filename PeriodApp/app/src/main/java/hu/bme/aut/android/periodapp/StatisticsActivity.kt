package hu.bme.aut.android.periodapp

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.android.periodapp.data.SymptomListDatabase
import hu.bme.aut.android.periodapp.databinding.ActivityStatisticsBinding
import java.time.Duration
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.concurrent.thread

class StatisticsActivity: AppCompatActivity() {

    private lateinit var binding: ActivityStatisticsBinding
    private lateinit var database: SymptomListDatabase

    @RequiresApi(33)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = SymptomListDatabase.getDatabase(applicationContext)

        binding.btnNotif.setOnClickListener{
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS,
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                showDummyNotification()
            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        calculate()
        create(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun calculate() {
        thread{
            val items=database.symptomItemDao().getAll()
            if(items.isEmpty())return@thread

            val itemIterator = items.iterator()
            var dates= mutableListOf<String>()

            //get the period day dates into dates
            while (itemIterator.hasNext()) {
                var item = itemIterator.next()
                if(item.category=="BLEEDING") {
                    if (dates.isEmpty() || dates.last() != item.date) {
                        dates.add(item.date)
                        dates.sort()
                    }
                }
            }
            var sumP=0
            var sumC=0
            var n=0
            var currP=0
            var firstDayOfPeriod="nodata"
            val dateIterator=dates.iterator()
            var lastDate=""
            while(dateIterator.hasNext()){
                var date=dateIterator.next()
                //egymást követő napok
                if(getDay(date)-getDay(lastDate)==1)
                    currP++
                else{
                    n++
                    sumP+=currP
                    currP=1
                    if(firstDayOfPeriod!="nodata")
                        sumC+=getDay(date)-getDay(firstDayOfPeriod)
                    firstDayOfPeriod=date
                }
                lastDate=date
            }
            sumP+=currP

            var div=1
            if(n>0)div=n
            binding.tvPL.text = (sumP/div).toString()+" days"
            div=1
            if(n-1>0)div=n-1
            binding.tvACL.text = (sumC/div).toString()+" days"
            val format = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val date = LocalDate.parse(firstDayOfPeriod,format)
            binding.tvNPD.text = format.format(date.plusDays((sumC/div).toLong()))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDay(string: String): Int{
        if(string.length<9) return -1

        val format = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = LocalDate.parse(string,format)
        val firstTimestampInclusive = LocalDate.of(1900, 1, 1)
        val numberOfDays = Duration.between(firstTimestampInclusive.atStartOfDay(), date.atStartOfDay()).toDays()
        return numberOfDays.toInt()
    }

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private val notificationManager: NotificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    @RequiresApi(33)
    fun create(savedInstanceState: Bundle?) {
        // Sets up permissions request launcher.
        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                showDummyNotification()
            } else {
                Snackbar.make(
                    findViewById<View>(android.R.id.content).rootView,
                    "Please grant Notification permission from App Settings",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

        // Sets up notification channel.
        createNotificationChannel()
    }

    /**
     * Creates Notification Channel (required for API level >= 26) before sending any notification.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Important Notification Channel",
            NotificationManager.IMPORTANCE_HIGH,
        ).apply {
            description = "This notification contains important announcement, etc."
        }
        notificationManager.createNotificationChannel(channel)
    }

    /**
     * Shows a notification to user.
     *
     * The notification won't appear if the user doesn't grant notification permission first.
     */
    @SuppressLint("MissingPermission")
    private fun showDummyNotification() {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Congratulations! 🎉🎉🎉")
            .setContentText("You have post a notification to Android 13!!!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(this)) {
            notify(1, builder.build())
        }
    }

    companion object {
        const val CHANNEL_ID = "dummy_channel"
    }
}