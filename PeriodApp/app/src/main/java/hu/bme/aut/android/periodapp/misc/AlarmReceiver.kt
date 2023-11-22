package hu.bme.aut.android.periodapp.misc

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import hu.bme.aut.android.periodapp.R
import hu.bme.aut.android.periodapp.StatisticsActivity

class AlarmReceiver : BroadcastReceiver() {

    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context, intent: Intent?) {
        Log.d("this", "notify")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val builder = NotificationCompat.Builder(context, StatisticsActivity.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_blood_foreground)
                .setContentTitle("Period might start soon-ish\uD83E\uDD14")
                .setContentText("Stay tuned\uD83D\uDE43\uD83E\uDD72")
                .setColor(ContextCompat.getColor(context, R.color.themeColor))
                .setPriority(NotificationCompat.PRIORITY_HIGH)

            with(NotificationManagerCompat.from(context)) {
                notify(1, builder.build())

            }

        }
    }
}