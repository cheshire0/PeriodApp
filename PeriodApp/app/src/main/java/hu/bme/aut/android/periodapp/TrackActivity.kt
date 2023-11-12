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
import kotlin.concurrent.thread

class TrackActivity: AppCompatActivity(), SymptomAdapter.SymptomItemClickListener {

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

        database = SymptomListDatabase.getDatabase(applicationContext)

        binding.fab.setOnClickListener {
            //TODO
        }
        initRecyclerView()
        val date = this.intent.getStringExtra("date")
        /*binding.idTVDate.setText(date)

        binding.btnSave.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
        }*/
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
}