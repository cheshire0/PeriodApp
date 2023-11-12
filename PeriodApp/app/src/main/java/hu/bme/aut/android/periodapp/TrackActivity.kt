package hu.bme.aut.android.periodapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.periodapp.adapter.SymptomAdapter
import hu.bme.aut.android.periodapp.data.SymptomItem
import hu.bme.aut.android.periodapp.data.SymptomListDatabase
import hu.bme.aut.android.periodapp.databinding.ActivityTrackBinding
import hu.bme.aut.android.periodapp.fragments.NewSymptomItemDialogFragment
import kotlin.concurrent.thread

class TrackActivity: AppCompatActivity(), SymptomAdapter.SymptomItemClickListener,
    NewSymptomItemDialogFragment.NewShoppingItemDialogListener{

    private lateinit var binding: ActivityTrackBinding

    private lateinit var database: SymptomListDatabase
    private lateinit var adapter: SymptomAdapter

    override fun onItemChanged(item: SymptomItem) {
        thread {
            database.symptomItemDao().update(item)
            Log.d("MainActivity", "ShoppingItem update was successful")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityTrackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        val date = this.intent.getStringExtra("date")
        supportActionBar?.setTitle("⋆｡ﾟ☁︎ ｡⋆  "+date+"  ｡ ﾟ☾ ﾟ｡⋆")
        binding.toolbar.setTitleTextColor(getResources().getColor(android.R.color.white))

        database = SymptomListDatabase.getDatabase(applicationContext)

        binding.fab.setOnClickListener {
            NewSymptomItemDialogFragment().show(
                supportFragmentManager,
                NewSymptomItemDialogFragment.TAG
            )
        }
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
                adapter.update(items)
            }
        }
    }

    override fun onShoppingItemCreated(newItem: SymptomItem) {
        thread {
            val insertId = database.symptomItemDao().insert(newItem)
            newItem.id = insertId
            runOnUiThread {
                adapter.addItem(newItem)
            }
    }
} }