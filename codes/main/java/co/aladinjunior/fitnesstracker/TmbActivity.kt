package co.aladinjunior.fitnesstracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import co.aladinjunior.fitnesstracker.model.Calc

class TmbActivity : AppCompatActivity() {

    private lateinit var lifestyle: AutoCompleteTextView
    private lateinit var button: Button
    private lateinit var txtHeight: EditText
    private lateinit var txtWeight: EditText
    private lateinit var txtAge: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tmb)

        lifestyle = findViewById(R.id.auto_lifestyle)
        val items = resources.getStringArray(R.array.tmb_lifestyle)
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,items)
        lifestyle.setAdapter(adapter)

        button = findViewById(R.id.tmb_button)
        txtHeight = findViewById(R.id.tmb_height)
        txtWeight = findViewById(R.id.tmb_weight)
        txtAge = findViewById(R.id.tmb_age)

        button.setOnClickListener {
            when(isNotValid()){
                true -> Toast.makeText(this,"Fields can't start with 0 or be empty!",Toast.LENGTH_SHORT).show()
                else -> {
                    val age = txtAge.text.toString().toInt()
                    val height = txtHeight.text.toString().toInt()
                    val weight = txtWeight.text.toString().toInt()
                    val tmb = calculateTMB(height, weight, age)
                    val calorieVal = getCalorieVal(tmb)
                    AlertDialog.Builder(this)
                        .setMessage(getString(R.string.tmb_dialog_message, calorieVal))
                        .setPositiveButton(android.R.string.ok){_,_ ->
                        }
                        .setNegativeButton(R.string.save_bttn){_,_->
                            Thread{
                                val app = application as App
                                val dao = app.db.calcDao()
                                val insert = dao.insert(Calc(type = "tmb",value = calorieVal))
                                runOnUiThread {
                                    val intent = Intent(this,ListCalcActivity::class.java)
                                        .putExtra("type","tmb")
                                    startActivity(intent)
                                }
                            }.start()
                        }
                        .create()
                        .show()

                }
            }
        }

    }

    private fun isNotValid() : Boolean{
        val age = txtAge.text.toString()
        val height = txtHeight.text.toString()
        val weight = txtWeight.text.toString()
        return (age.isEmpty() || height.isEmpty() || weight.isEmpty() ||
                age.startsWith("0") || height.startsWith("0") ||
                weight.startsWith("0"))
    }

    private fun calculateTMB(height: Int, weight: Int, age: Int) : Double{
        return 66 + (13.8 * weight) + (5 * height) - (6.8 * age)
    }

    private fun getCalorieVal(tmb: Double) : Double {
        val exLevel = resources.getStringArray(R.array.tmb_lifestyle)
        return when{
            lifestyle.text.toString().equals(exLevel[0]) -> tmb * 1.375
            lifestyle.text.toString().equals(exLevel[1]) -> tmb * 1.55
            lifestyle.text.toString().equals(exLevel[2]) -> tmb * 1.725
            else -> 0.0
        }




    }
}