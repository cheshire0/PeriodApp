package hu.bme.aut.android.periodapp

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.widget.CalendarView.OnDateChangeListener
import androidx.appcompat.app.AppCompatActivity
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import hu.bme.aut.android.periodapp.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTrack.setOnClickListener{
            val intent = Intent(this, TrackActivity::class.java)
            intent.putExtra("date", binding.idTVDate.text)
            startActivity(intent)
        }

        val today = Calendar.getInstance().timeInMillis
        val format = SimpleDateFormat("yyyy-MM-dd")
        binding.idTVDate.setText(format.format(today))

        binding.calendarView.addDecorator(CurrentDayDecorator(this@MainActivity, CalendarDay.today()))
        binding.calendarView
            .setOnDateChangedListener(
                OnDateSelectedListener { view, calDay, bool ->
                    val Date = (calDay.year.toString() + "-" + (calDay.month) + "-" + calDay.day)

                    binding.idTVDate.setText(Date)
                })
    }
}