package hu.bme.aut.android.periodapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.periodapp.R
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

        holder.binding.ibRemove.setOnClickListener {
            delete(symptomItem)
            listener.onItemDeleted(symptomItem)
        }
        holder.binding.ibEdit.setOnClickListener {
            //edit(symptomItem)
            listener.onItemEdited(symptomItem)
        }
        holder.binding.tvCategory.text =symptomItem.category
        when(symptomItem.category){
            "BLEEDING"->holder.binding.ivIcon.setImageResource(R.drawable.ic_blood_foreground)
            "PAIN"->holder.binding.ivIcon.setImageResource(R.drawable.ic_pain_foreground)
            "EMOTIONS"->holder.binding.ivIcon.setImageResource(R.drawable.ic_emotion_foreground)
            "HUNGER"->holder.binding.ivIcon.setImageResource(R.drawable.ic_hunger_foreground)
        }
        holder.binding.tvSymptom.text = symptomItem.type
        holder.binding.tvDescription.text = symptomItem.description
    }

    fun addItem(item: SymptomItem) {
        items.add(item)
        notifyItemInserted(items.size - 1)
        listener.onItemChanged(item)
    }

    fun update(symptomItems: List<SymptomItem>,date:String) {
        items.clear()
        items.addAll(symptomItems)
        val itemIterator = items.iterator()
        while (itemIterator.hasNext()) {
            val item = itemIterator.next()
            if (item.date!=date) {
                itemIterator.remove()
            }
        }
        notifyDataSetChanged()
    }

    fun delete(shoppingItem: SymptomItem){
        val n=items.indexOf(shoppingItem)
        items.remove(shoppingItem)
        notifyItemRemoved(n)
    }

    fun edit(shoppingItem: SymptomItem, oldItem: SymptomItem) {
        val n=items.indexOf(oldItem)
        items.removeAt(n)
        items.add(n,shoppingItem)
        listener.onItemChanged(shoppingItem)
        notifyItemChanged(n)
    }

    override fun getItemCount(): Int = items.size

    interface SymptomItemClickListener {
        fun onItemChanged(item: SymptomItem)
        fun onItemDeleted(item: SymptomItem)
        fun onItemEdited(item: SymptomItem)
    }

    inner class SymptomViewHolder(val binding: ItemSymptomListBinding) : RecyclerView.ViewHolder(binding.root)
}