package hu.bme.aut.android.periodapp.misc

import android.app.Activity
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import hu.bme.aut.android.periodapp.R


class CurrentDayDecorator(context: Activity?, currentDay: CalendarDay) : DayViewDecorator {
    private val drawable: Drawable?
    var myDay = currentDay
    override fun shouldDecorate(day: CalendarDay): Boolean {
        return myDay==day
    }

    override fun decorate(view: DayViewFacade) {
        view.setBackgroundDrawable(drawable!!)
    }

    init {
        // You can set background for Decorator via drawable here
        drawable = ContextCompat.getDrawable(context!!, R.drawable.red_dot_foreground)
    }
}