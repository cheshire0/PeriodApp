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

class NewSymptomItemDialogFragment  : DialogFragment() {
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
        binding.spBleeding.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(R.array.bleeding_items)
        )
        binding.spPain.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(R.array.pain_items)
        )
        binding.spEmotions.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(R.array.emotions_items)
        )
        binding.spOther.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(R.array.other_items)
        )

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.new_symptom_item)
            .setView(binding.root)
            .setPositiveButton(R.string.button_ok) { dialogInterface, i ->
                if (true) {
                    listener.onShoppingItemCreated(getSymptomItem())
                }
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
        other = SymptomItem.Other.getByOrdinal(binding.spOther.selectedItemPosition)
            ?: SymptomItem.Other.LOW_HUNGER,
        date="bruv",
        description="bruh"
    )

    companion object {
        const val TAG = "NewShoppingItemDialogFragment"
    }
}