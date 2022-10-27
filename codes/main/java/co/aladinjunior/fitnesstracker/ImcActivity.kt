package co.aladinjunior.fitnesstracker


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import co.aladinjunior.fitnesstracker.model.Calc
import kotlin.math.pow

class ImcActivity : AppCompatActivity() {

    private lateinit var editTxtHeight: EditText
    private lateinit var editTxtWeight: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc)

        editTxtHeight = findViewById(R.id.txt_height)
        editTxtWeight = findViewById(R.id.txt_weight)
        val mainButton = findViewById<Button>(R.id.main_Bttn)
        mainButton.setOnClickListener {
            when (!isValid()) {
                true ->
                    Toast.makeText(this, R.string.invalid_field, Toast.LENGTH_SHORT).show()
            }

            val intWeight = editTxtWeight.text.toString().toInt()
            val intHeight = editTxtHeight.text.toString().toInt()



            val imcValue = calculateIMC(intWeight,intHeight)
            val categoryId = imcCategory(imcValue)

            AlertDialog.Builder(this)
                .setTitle(getString(R.string.imc_dialog_title,imcValue))
                .setMessage(categoryId)
                .setPositiveButton(android.R.string.ok){_,_ ->
                    Thread{
                        val app = application as App
                        val dao = app.db.calcDao()
                        dao.insert(Calc(type = "imc",value = imcValue))
                        runOnUiThread {
                            val intent = Intent(this,ListCalcActivity::class.java)
                                .putExtra("type","imc")
                            startActivity(intent)
                        }
                    }.start()
                }
                .create().show()
            val service = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            service.hideSoftInputFromWindow(currentFocus?.windowToken,0)

        }





    }
    @StringRes
    private fun imcCategory(imc: Double): Int{
        return when{
            imc < 18.0 -> R.string.imc_underweight
            imc < 25.0 -> R.string.imc_normal
            imc < 30.0 -> R.string.imc_overweight
            imc < 40.0 -> R.string.imc_obesity
            else -> R.string.imc_severe_obesity
        }
    }





    private fun calculateIMC(weight: Int, height: Int): Double{

        return weight / ((height.toDouble().pow(2) / 10000.0))

    }

    private fun isValid(): Boolean {
        val sWeight = editTxtWeight.text.toString()
        val sHeight = editTxtHeight.text.toString()
        return (sWeight.isNotEmpty()
                && sHeight.isNotEmpty()
                && !sWeight.startsWith("0")
                && !sHeight.startsWith("0"))
    }
}
