package hu.bme.aut.android.periodapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.periodapp.data.SymptomItem
import hu.bme.aut.android.periodapp.databinding.ItemSymptomListBinding

class SymptomAdapter (private val listener: SymptomItemClickListener) :
RecyclerView.Adapter<SymptomAdapter.SymptomViewHolder>() {

    private val items = mutableListOf<SymptomItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SymptomViewHolder(
        ItemSymptomListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: SymptomViewHolder, position: Int) {
        val symptomItem = items[position]

        //holder.binding.ivIcon.setImageResource(getImageResource(shoppingItem.category))
        //holder.binding.cbIsBought.isChecked = shoppingItem.isBought
        holder.binding.tvDate.text=symptomItem.date
        holder.binding.tvBleeding.text = symptomItem.bleeding.name
        holder.binding.tvPain.text = symptomItem.pain.name
        holder.binding.tvEmotions.text = symptomItem.emotions.name
        holder.binding.tvOther.text = symptomItem.hunger.name
    }

    fun addItem(item: SymptomItem) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun update(symptomItems: List<SymptomItem>) {
        items.clear()
        items.addAll(symptomItems)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    interface SymptomItemClickListener {
        fun onItemChanged(item: SymptomItem)
    }

    inner class SymptomViewHolder(val binding: ItemSymptomListBinding) : RecyclerView.ViewHolder(binding.root)
}