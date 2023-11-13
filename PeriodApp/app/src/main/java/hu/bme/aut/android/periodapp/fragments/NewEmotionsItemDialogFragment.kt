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
import hu.bme.aut.android.periodapp.databinding.DialogNewEmotionsItemBinding
import hu.bme.aut.android.periodapp.databinding.DialogNewSymptomItemBinding

class NewEmotionsItemDialogFragment(var date: String)  : DialogFragment() {
    interface NewSymptomItemDialogListener {
        fun onSymptomItemCreated(newItem: SymptomItem)
    }

    private lateinit var listener: NewSymptomItemDialogListener

    private lateinit var binding: DialogNewEmotionsItemBinding
    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NewSymptomItemDialogListener
            ?: throw RuntimeException("Activity must implement the NewShoppingItemDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogNewEmotionsItemBinding.inflate(LayoutInflater.from(context))
        binding.spEmotions.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(R.array.emotions_items)
        )

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.new_symptom_item)
            .setView(binding.root)
            .setPositiveButton(R.string.button_ok) { dialogInterface, i ->
                listener.onSymptomItemCreated(getSymptomItem())
            }
            .setNegativeButton(R.string.button_cancel, null)
            .create()
    }
    private fun getSymptomItem() = SymptomItem(
        emotions = SymptomItem.Emotions.getByOrdinal(binding.spEmotions.selectedItemPosition)
            ?: SymptomItem.Emotions.HAPPY,
        date=date,
        type="EMOTIONS",
        description =binding.etDescription.text.toString()
    )

    companion object {
        const val TAG = "NewSymptomItemDialogFragment"
    }
}