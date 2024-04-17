/*
11056021李恩
11056027許閔涵
11056044李佳穎
 */


package imd.ntub.orderapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import imd.ntub.orderapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var edtName: EditText
    private lateinit var edtMeal: EditText
    private lateinit var edtDrink: EditText
    private lateinit var edtDesert: EditText
    private lateinit var edtAdd: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        edtName = binding.edtName
        edtMeal = binding.edtMeal // 初始化
        edtDrink = binding.edtDrink
        edtDesert = binding.edtDesert
        edtAdd = binding.edtAdd


        binding.btnOrder.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val totalAmount = data?.getIntExtra("TOTAL_AMOUNT", 0)
            edtName.setText("$" + totalAmount.toString())
            edtName.textSize = 18f // 設定文字大小為 18 sp

            // 传递主餐数据
            val selectedMeal = data?.getStringExtra("SELECTED_MEAL")
            if (!selectedMeal.isNullOrEmpty()) {
                edtMeal.setText(selectedMeal)
                edtMeal.textSize = 18f // 設定文字大小為 18 sp
            }

            val selectedDrink = data?.getStringExtra("SELECTED_DRINK")
            if (!selectedDrink.isNullOrEmpty()) {
                edtDrink.setText(selectedDrink)
                edtDrink.textSize = 18f // 設定文字大小為 18 sp
            }

            val selectedDesert = data?.getStringExtra("SELECTED_DESERT")
            if (!selectedDesert.isNullOrEmpty()) {
                edtDesert.setText(selectedDesert)
                edtDesert.textSize = 18f // 設定文字大小為 18 sp
            }

            val selectedAdd = data?.getStringExtra("SELECTED_ADD")
            if (!selectedAdd.isNullOrEmpty()) {
                edtAdd.setText(selectedAdd)
                edtAdd.textSize = 18f // 設定文字大小為 18 sp
            } else {
                edtAdd.setText("無") // 清空加点内容
                edtAdd.textSize = 18f // 設定文字大小為 18 sp
            }
        }
    }


}
