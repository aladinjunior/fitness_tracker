package co.aladinjunior.fitnesstracker

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var mainRv: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val items = mutableListOf<MainItems>()

        items.add(
            MainItems(
                1,
                R.string.imc_calculator,
                R.drawable.ic_baseline_fitness_center_24,
                Color.TRANSPARENT
            )
        )
        items.add(
            MainItems(
                2,
                R.string.tmb_calculator,
                R.drawable.ic_baseline_directions_walk_24,
                Color.TRANSPARENT
            )
        )


        mainRv = findViewById(R.id.main_rv)
        mainRv.adapter = MainAdapter(items){
            when(it){
                1 -> {
                    openListCalcActivity()
                }
            }
        }

        mainRv.layoutManager = GridLayoutManager(this, 2)





    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId == R.id.menu_search){
            true ->{
                openListCalcActivity()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openListCalcActivity(){
        val intent = Intent(this@MainActivity, ImcActivity::class.java)
        startActivity(intent)
    }



    private inner class MainAdapter(private val items: List<MainItems>,
    private val onItemClickListener: (Int) -> Unit) :
        RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val view = layoutInflater.inflate(R.layout.main_item, parent, false)
            return MainViewHolder(view)
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val currentItem = items[position]
            holder.bind(currentItem)

        }

        override fun getItemCount(): Int {
            return items.size
        }

        private inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            fun bind(item: MainItems) {
                val imgIcon: ImageView = itemView.findViewById(R.id.item_img_icon)
                val nameIcon: TextView = itemView.findViewById(R.id.item_txtName_icon)
                val container: LinearLayout =
                    itemView.findViewById(R.id.item_layout) as LinearLayout

                imgIcon.setImageResource(item.drawableId)
                nameIcon.setText(item.strinTxtId)
                container.setBackgroundColor(item.color)

                container.setOnClickListener {
                    onItemClickListener.invoke(item.id)
                }

            }
        }

    }
}




