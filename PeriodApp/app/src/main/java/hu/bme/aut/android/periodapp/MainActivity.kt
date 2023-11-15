package hu.bme.aut.android.periodapp

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import hu.bme.aut.android.periodapp.data.SymptomListDatabase
import hu.bme.aut.android.periodapp.databinding.ActivityMainBinding
import java.util.Calendar
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: SymptomListDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = SymptomListDatabase.getDatabase(applicationContext)

        binding.btnTrack.setOnClickListener{
            val intent = Intent(this, TrackActivity::class.java)
            intent.putExtra("date", binding.idTVDate.text)
            startActivity(intent)
        }

        val today = Calendar.getInstance().timeInMillis
        val format = SimpleDateFormat("yyyy-MM-dd")
        binding.idTVDate.setText(format.format(today))

        decorate()
        binding.calendarView
            .setOnDateChangedListener(
                OnDateSelectedListener { view, calDay, bool ->
                    val Date = (calDay.year.toString() + "-" + (calDay.month) + "-" + calDay.day)

                    binding.idTVDate.setText(Date)
                })
    }

    private fun decorate(){
        thread {
            val today = Calendar.getInstance().timeInMillis
            val format = SimpleDateFormat("yyyy-MM-dd")
            val items = database.symptomItemDao().getAll()
            val itemIterator = items.iterator()
            while (itemIterator.hasNext()) {
                val item = itemIterator.next()
                if (item.bleeding!=null) {
                    val year=item.date.get(0).toString()+item.date.get(1)+item.date.get(2)+item.date.get(3)
                    val month=item.date.get(5).toString()+item.date.get(6)
                    var day=item.date.get(8).toString()
                    if(item.date.length>9) day=day+item.date.get(9)
                    binding.calendarView.addDecorator(
                        CurrentDayDecorator(this@MainActivity, CalendarDay.from(year.toInt(),month.toInt(),day.toInt()))
                    )
                }
            }
        }
    }
}