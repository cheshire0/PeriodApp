package hu.bme.aut.android.periodapp.misc

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.periodapp.R
import hu.bme.aut.android.periodapp.data.SymptomItem
import hu.bme.aut.android.periodapp.data.categories.Bleeding
import hu.bme.aut.android.periodapp.data.categories.Emotions
import hu.bme.aut.android.periodapp.data.categories.Hunger
import hu.bme.aut.android.periodapp.data.categories.Pain
import hu.bme.aut.android.periodapp.databinding.DialogNewSymptomItemBinding

class NewSymptomItemDialogFragment(
    var date: String, var category: String, private var types: Array<String>,
    var item: SymptomItem? ) : DialogFragment() {
    private lateinit var binding: DialogNewSymptomItemBinding
    private lateinit var listener: NewSymptomItemDialogListener

    interface NewSymptomItemDialogListener {
        fun onSymptomItemCreated(newItem: SymptomItem)
        fun onItemEdited(newItem: SymptomItem,oldItem: SymptomItem)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NewSymptomItemDialogListener
            ?: throw RuntimeException("Activity must implement the NewShoppingItemDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogNewSymptomItemBinding.inflate(LayoutInflater.from(context))

        binding.tvSymptom.text = category
        binding.spSymptom.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            types
        )

        if(item!=null) { setSymptomItem(item!!) }

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.new_symptom_item)
            .setView(binding.root)
            .setPositiveButton(R.string.button_ok) { dialogInterface, i ->
                if(item!=null) listener.onItemEdited(getSymptomItem(), item!!)
                else listener.onSymptomItemCreated(getSymptomItem())
            }
            .setNegativeButton(R.string.button_cancel, null)
            .create()
    }

    private fun setSymptomItem(item: SymptomItem) {
        binding.tvSymptom.text=category
        binding.spSymptom.setSelection((binding.spSymptom.adapter as ArrayAdapter<String>).getPosition(item.type))
        binding.etDescription.setText(item.description)
    }

    private fun getSymptomItem() = SymptomItem(
        type = getType(),
        date =date,
        category =category.uppercase(),
        description =binding.etDescription.text.toString()
    )

    private fun getType(): String {
        when(category.uppercase()){
            "BLEEDING"->return Bleeding.getByOrdinal(binding.spSymptom.selectedItemPosition).toString()
            "PAIN"->return Pain.getByOrdinal(binding.spSymptom.selectedItemPosition).toString()
            "EMOTIONS"->return Emotions.getByOrdinal(binding.spSymptom.selectedItemPosition).toString()
            "HUNGER"->return Hunger.getByOrdinal(binding.spSymptom.selectedItemPosition).toString()
        }
        return ""
    }

    companion object {
        const val TAG = "NewSymptomItemDialogFragment"
    }
}