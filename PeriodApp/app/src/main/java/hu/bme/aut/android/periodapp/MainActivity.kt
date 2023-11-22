package hu.bme.aut.android.periodapp

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import hu.bme.aut.android.periodapp.data.SymptomListDatabase
import hu.bme.aut.android.periodapp.databinding.ActivityMainBinding
import hu.bme.aut.android.periodapp.misc.CurrentDayDecorator
import java.util.Calendar
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: SymptomListDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding=ActivityMainBinding.inflate(layoutInflater)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(binding.root)
        database = SymptomListDatabase.getDatabase(applicationContext)

        binding.btnTrack.setOnClickListener{
            val intent = Intent(this, TrackActivity::class.java)
            intent.putExtra("date", binding.idTVDate.text)
            startActivity(intent)
        }
        binding.btnStats.setOnClickListener{
            val intent = Intent(this, StatisticsActivity::class.java)
            startActivity(intent)
        }

        val today = Calendar.getInstance().timeInMillis
        val format = SimpleDateFormat("yyyy-MM-dd")
        binding.idTVDate.text = format.format(today)

        binding.calendarView
            .setOnDateChangedListener(
                OnDateSelectedListener { _, calDay, _ ->
                    val date = (calDay.year.toString() + "-" + (calDay.month) + "-" + calDay.day)

                    binding.idTVDate.text = date
                })
    }

    override fun onResume() {
        super.onResume()
        decorate()
    }

    private fun decorate(){
        thread {
            val items = database.symptomItemDao().getAll()
            runOnUiThread {
                binding.calendarView.removeDecorators()
                val itemIterator = items.iterator()
                while (itemIterator.hasNext()) {
                    val item = itemIterator.next()
                    if (item.category == "BLEEDING") {
                        val year = item.date[0]
                            .toString() + item.date[1] + item.date[2] + item.date[3]
                        val month = item.date[5].toString() + item.date[6]
                        var day = item.date[8].toString()
                        if (item.date.length > 9) day += item.date[9]
                        binding.calendarView.addDecorator(
                            CurrentDayDecorator(
                                this@MainActivity,
                                CalendarDay.from(year.toInt(), month.toInt(), day.toInt())
                            )
                        )
                    }
                }
            }
        }
    }
}