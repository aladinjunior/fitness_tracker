package co.aladinjunior.fitnesstracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.aladinjunior.fitnesstracker.model.Calc
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import java.util.zip.Inflater

class ListCalcActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_calc)

        val type = intent?.extras?.getString("type") ?: throw Exception("no type found")

        val datalist = mutableListOf<Calc>()
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = ListCalcAdapter(datalist)
        recyclerView.adapter = adapter




        Thread{
            val app = application as App
            val dao = app.db.calcDao()
            val database = dao.getValuesByType(type)
            runOnUiThread {
                datalist.addAll(database)
                adapter.notifyDataSetChanged()
            }
        }.start()

    }

    private inner class ListCalcAdapter(private val datalist: List<Calc>) : RecyclerView.Adapter<ListCalcViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCalcViewHolder {
            val view = layoutInflater.inflate(android.R.layout.simple_list_item_1,parent,false)
            return ListCalcViewHolder(view)
        }

        override fun onBindViewHolder(holder: ListCalcViewHolder, position: Int) {
            val currentList = datalist[position]
            holder.bind(currentList)
        }


        override fun getItemCount(): Int {
            return datalist.size
        }



    }

    private inner class ListCalcViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(currentList: Calc) {
            val allData = itemView as TextView
            val date = SimpleDateFormat("dd/MM/yyyy", Locale("pt","BR"))
                .format(currentList.createdDate)
            val value = currentList.value
            allData.text = getString(R.string.list_response,value,date)
        }
    }
}