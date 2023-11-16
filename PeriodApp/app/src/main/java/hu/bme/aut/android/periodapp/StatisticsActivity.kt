package hu.bme.aut.android.periodapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.prolificinteractive.materialcalendarview.CalendarDay
import hu.bme.aut.android.periodapp.data.SymptomItemDao
import hu.bme.aut.android.periodapp.data.SymptomListDatabase
import hu.bme.aut.android.periodapp.databinding.ActivityStatisticsBinding
import kotlin.concurrent.thread

class StatisticsActivity: AppCompatActivity() {

    private lateinit var binding: ActivityStatisticsBinding
    private lateinit var database: SymptomListDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = SymptomListDatabase.getDatabase(applicationContext)

        calculate()
    }

    private fun calculate() {
        thread{
            val items=database.symptomItemDao().getAll()
            if(items.isEmpty())return@thread

            val itemIterator = items.iterator()
            var sumP=0
            var sumC=0
            var n=0
            var currP=0
            var currC=0
            var currDate: String
            var dates= mutableListOf<String>()

            var daaate=""
            while (itemIterator.hasNext()) {
                var item = itemIterator.next()
                dates.sort()
                if(item.category=="BLEEDING") {
                    daaate=daaate+item.date
                    if (dates.isEmpty() || dates.last() != item.date)
                        dates.add(item.date)
                }
            }
            //binding.tvPL.setText((sumP/n).toString())
            binding.tvPL.setText(daaate)
        }
    }
}