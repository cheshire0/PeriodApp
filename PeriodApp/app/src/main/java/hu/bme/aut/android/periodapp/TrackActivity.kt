package hu.bme.aut.android.periodapp

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.SubMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.periodapp.adapter.SymptomAdapter
import hu.bme.aut.android.periodapp.data.SymptomItem
import hu.bme.aut.android.periodapp.data.SymptomListDatabase
import hu.bme.aut.android.periodapp.databinding.ActivityTrackBinding
import hu.bme.aut.android.periodapp.misc.NewSymptomItemDialogFragment
import kotlin.concurrent.thread

class TrackActivity: AppCompatActivity(), SymptomAdapter.SymptomItemClickListener,
    NewSymptomItemDialogFragment.NewSymptomItemDialogListener{

    private lateinit var binding: ActivityTrackBinding

    private lateinit var database: SymptomListDatabase
    private lateinit var adapter: SymptomAdapter
    private lateinit var date: String

    override fun onItemChanged(item: SymptomItem) {
        thread {
            database.symptomItemDao().update(item)
            Log.d("TrackActivity", "ShoppingItem update was successful")
        }
    }
    override fun onItemDeleted(item: SymptomItem) {
        thread {
            database.symptomItemDao().deleteItem(item)
            Log.d("TrackActivity", "ShoppingItem delete was successful")
        }
    }

    private fun String.titlecaseFirstChar() = replaceFirstChar(Char::titlecase)

    override fun onItemEdited(item: SymptomItem) {
        var array=resources.getStringArray(R.array.bleeding_items)
        when(item.category){
            "BLEEDING"->array=resources.getStringArray(R.array.bleeding_items)
            "PAIN"->array=resources.getStringArray(R.array.pain_items)
            "EMOTIONS"->array=resources.getStringArray(R.array.emotions_items)
            "HUNGER"->array=resources.getStringArray(R.array.hunger_items)
        }
        NewSymptomItemDialogFragment(item.date,item.category.lowercase().titlecaseFirstChar(),array,item).show(
            supportFragmentManager,
            NewSymptomItemDialogFragment.TAG
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityTrackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        date = this.intent.getStringExtra("date").toString()
        val title= "⋆｡ﾟ☁︎ ｡⋆  $date  ｡ ﾟ☾ ﾟ｡⋆"
        binding.toolbarTitle.text = title
        binding.toolbar.setTitleTextColor(resources.getColor(android.R.color.white))

        database = SymptomListDatabase.getDatabase(applicationContext)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = SymptomAdapter(this)
        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.adapter = adapter
        loadItemsInBackground()
    }

    private fun loadItemsInBackground() {
        thread {
            val items = database.symptomItemDao().getAll()
            runOnUiThread {
                adapter.update(items,date)
            }
        }
    }

    override fun onSymptomItemCreated(newItem: SymptomItem) {
        thread {
            val insertId = database.symptomItemDao().insert(newItem)
            newItem.id = insertId
            runOnUiThread {
                adapter.addItem(newItem)
            }
        }
    }

    override fun onSymptomItemEdited(newItem: SymptomItem, oldItem: SymptomItem) {
        //onSymptomItemCreated(newItem)
        //onItemDeleted(oldItem)
        newItem.id=oldItem.id
        adapter.edit(newItem,oldItem)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val toolbarMenu: Menu = binding.toolbar.menu
        menuInflater.inflate(R.menu.menu_toolbar, toolbarMenu)
        for (i in 0 until toolbarMenu.size()) {
            val menuItem: MenuItem = toolbarMenu.getItem(i)
            menuItem.setOnMenuItemClickListener { item -> onOptionsItemSelected(item) }
            if (menuItem.hasSubMenu()) {
                val subMenu: SubMenu = menuItem.subMenu!!
                for (j in 0 until subMenu.size()) {
                    subMenu.getItem(j)
                        .setOnMenuItemClickListener { item -> onOptionsItemSelected(item) }
                }
            }
        }
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_bleeding -> {
                NewSymptomItemDialogFragment(date,resources.getString(R.string.bleeding),resources.getStringArray(R.array.bleeding_items),null).show(
                    supportFragmentManager,
                    NewSymptomItemDialogFragment.TAG
                )
                true
            }
            R.id.menu_pain -> {
                NewSymptomItemDialogFragment(date,resources.getString(R.string.pain),resources.getStringArray(R.array.pain_items),null).show(
                    supportFragmentManager,
                    NewSymptomItemDialogFragment.TAG
                )
                true
            }
            R.id.menu_emotions -> {
                NewSymptomItemDialogFragment(date,resources.getString(R.string.emotions),resources.getStringArray(R.array.emotions_items),null).show(
                    supportFragmentManager,
                    NewSymptomItemDialogFragment.TAG
                )
                true
            }
            R.id.menu_hunger -> {
                NewSymptomItemDialogFragment(date,resources.getString(R.string.hunger),resources.getStringArray(R.array.hunger_items),null).show(
                    supportFragmentManager,
                    NewSymptomItemDialogFragment.TAG
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}