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
            var item = itemIterator.next()
            var dates: List<String>

            while (itemIterator.hasNext()) {
                currDate=item.date
                var wasPeriodDay=false

                while(item.date==currDate) {
                    if (item.category == "BLEEDING") {
                        wasPeriodDay = true
                        currP++
                        if(currP==1) n++
                        //onto the next day
                        while(item.date==currDate) item=itemIterator.next()
                        break
                    }
                    item = itemIterator.next()
                }
                if(!wasPeriodDay){
                    sumP+=currP
                    currP=0

                }
            }
            binding.tvPL.setText((sumP/n).toString())
            binding.tvPL.setText(currP.toString()+" "+sumP+" "+n)
        }
    }
}