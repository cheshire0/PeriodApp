package hu.bme.aut.android.periodapp

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.android.periodapp.data.SymptomListDatabase
import hu.bme.aut.android.periodapp.databinding.ActivityStatisticsBinding
import java.time.Duration
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class StatisticsActivity: AppCompatActivity() {

    private lateinit var binding: ActivityStatisticsBinding
    private lateinit var database: SymptomListDatabase

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = SymptomListDatabase.getDatabase(applicationContext)

        calculate()
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
}