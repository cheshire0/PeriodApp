package hu.bme.aut.android.periodapp

import android.content.Intent
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
import hu.bme.aut.android.periodapp.fragments.NewBleedingItemDialogFragment
import hu.bme.aut.android.periodapp.fragments.NewEmotionsItemDialogFragment
import hu.bme.aut.android.periodapp.fragments.NewHungerItemDialogFragment
import hu.bme.aut.android.periodapp.fragments.NewPainItemDialogFragment
import hu.bme.aut.android.periodapp.fragments.NewSymptomItemDialogFragment
import kotlin.concurrent.thread

class TrackActivity: AppCompatActivity(), SymptomAdapter.SymptomItemClickListener,
    NewBleedingItemDialogFragment.NewSymptomItemDialogListener,
    NewPainItemDialogFragment.NewSymptomItemDialogListener,
    NewEmotionsItemDialogFragment.NewSymptomItemDialogListener,
    NewHungerItemDialogFragment.NewSymptomItemDialogListener{

    private lateinit var binding: ActivityTrackBinding

    private lateinit var database: SymptomListDatabase
    private lateinit var adapter: SymptomAdapter
    private lateinit var date: String

    override fun onItemChanged(item: SymptomItem) {
        thread {
            database.symptomItemDao().update(item)
            Log.d("MainActivity", "ShoppingItem update was successful")
        }
    }
    override fun onItemDeleted(item: SymptomItem) {
        thread {
            database.symptomItemDao().deleteItem(item)
            Log.d("MainActivity", "ShoppingItem delete was successful")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityTrackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        date = this.intent.getStringExtra("date").toString()
        val title="⋆｡ﾟ☁︎ ｡⋆  "+date+"  ｡ ﾟ☾ ﾟ｡⋆"
        binding.toolbarTitle.setText(title)
        binding.toolbar.setTitleTextColor(getResources().getColor(android.R.color.white))

        database = SymptomListDatabase.getDatabase(applicationContext)

        binding.btnSave.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
        }
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
                NewBleedingItemDialogFragment(date).show(
                    supportFragmentManager,
                    NewBleedingItemDialogFragment.TAG
                )
                true
            }
            R.id.menu_pain -> {
                NewPainItemDialogFragment(date).show(
                    supportFragmentManager,
                    NewPainItemDialogFragment.TAG
                )
                true
            }
            R.id.menu_emotions -> {
                NewEmotionsItemDialogFragment(date).show(
                    supportFragmentManager,
                    NewSymptomItemDialogFragment.TAG
                )
                true
            }
            R.id.menu_hunger -> {
                NewHungerItemDialogFragment(date).show(
                    supportFragmentManager,
                    NewSymptomItemDialogFragment.TAG
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}