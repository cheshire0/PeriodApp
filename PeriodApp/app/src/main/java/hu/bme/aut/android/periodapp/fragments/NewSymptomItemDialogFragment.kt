package hu.bme.aut.android.periodapp.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.periodapp.R
import hu.bme.aut.android.periodapp.data.SymptomItem
import hu.bme.aut.android.periodapp.databinding.DialogNewSymptomItemBinding

class NewSymptomItemDialogFragment(var date: String,var type: String)  : DialogFragment() {
    interface NewShoppingItemDialogListener {
        fun onShoppingItemCreated(newItem: SymptomItem)
    }

    private lateinit var listener: NewShoppingItemDialogListener

    private lateinit var binding: DialogNewSymptomItemBinding
    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NewShoppingItemDialogListener
            ?: throw RuntimeException("Activity must implement the NewShoppingItemDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogNewSymptomItemBinding.inflate(LayoutInflater.from(context))
        if(type=="bleeding") {
            binding.spBleeding.adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                resources.getStringArray(R.array.bleeding_items)
            )
        }
        if(type=="pain") {
            binding.spPain.adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                resources.getStringArray(R.array.pain_items)
            )
        }
        if(type=="emotions") {
            binding.spEmotions.adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                resources.getStringArray(R.array.emotions_items)
            )
        }
        if(type=="hunger") {
            binding.spHunger.adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                resources.getStringArray(R.array.hunger_items)
            )
        }

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.new_symptom_item)
            .setView(binding.root)
            .setPositiveButton(R.string.button_ok) { dialogInterface, i ->
                listener.onShoppingItemCreated(getSymptomItem())
            }
            .setNegativeButton(R.string.button_cancel, null)
            .create()
    }
    private fun getSymptomItem() = SymptomItem(
        bleeding = SymptomItem.Bleeding.getByOrdinal(binding.spBleeding.selectedItemPosition)
            ?: SymptomItem.Bleeding.SPOTTING,
        pain = SymptomItem.Pain.getByOrdinal(binding.spPain.selectedItemPosition)
            ?: SymptomItem.Pain.CRAMPS,
        emotions = SymptomItem.Emotions.getByOrdinal(binding.spEmotions.selectedItemPosition)
            ?: SymptomItem.Emotions.HAPPY,
        hunger = SymptomItem.Hunger.getByOrdinal(binding.spHunger.selectedItemPosition)
            ?: SymptomItem.Hunger.LOW,
        date=date,
        type="bro",
        description =binding.etDescription.text.toString()
    )

    companion object {
        const val TAG = "NewSymptomItemDialogFragment"
    }
}