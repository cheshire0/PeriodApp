package hu.bme.aut.android.periodapp.misc

object DateParser {
    public fun dateParser(date:String): Triple<Int,Int,Int>{
        var year = ""
        var n=0
        while(date[n]!='-'){
            year+=date[n++]
        }
        n++
        var month = ""
        while(date[n]!='-'){
            month+=date[n++]
        }
        n++
        var day=""
        while(date.length>n){
            day+=date[n++]
        }
        return Triple(year.toInt(),month.toInt(),day.toInt())
    }
}