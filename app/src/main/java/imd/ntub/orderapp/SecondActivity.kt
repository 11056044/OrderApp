// SecondActivity.kt

package imd.ntub.orderapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import imd.ntub.orderapp.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    private var totalAmount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRadioButtons()
        setupCheckBoxes()
        setupCheckButton()
    }

    private fun setupRadioButtons() {
        val radioButtons = listOf<RadioButton>(
            binding.rbtnBeef, binding.rbtnChicken, binding.rbtnFish,
            binding.rbtnCoke,binding.rbtnTea,binding.rbtnSoup,
            binding.rbtnFries,binding.rbtnIce,binding.rbtnPie,
            binding.rbtnChickenNuggets,binding.rbtnBreakfast,binding.rbtnMushroom,binding.rbtnSpicyChicken
        )
        radioButtons.forEach { radioButton ->
            radioButton.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    val price = buttonView.tag.toString().toInt()
                    totalAmount += price
                } else {
                    val price = buttonView.tag.toString().toInt()
                    totalAmount -= price
                }
            }
        }
    }

    private fun setupCheckBoxes() {
        val checkBoxes = listOf<CheckBox>(
            binding.chbOriginal, binding.chbSpicy
        )
        checkBoxes.forEach { checkBox ->
            checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                val price = buttonView.tag.toString().toInt()
                if (isChecked) {
                    totalAmount += price
                } else {
                    totalAmount -= price
                }
            }
        }
    }

    private fun setupCheckButton() {
        binding.btnCheck.setOnClickListener {
            showConfirmationDialog()
        }
    }

    private fun showConfirmationDialog() {
        val selectedMealId = binding.rdgMeal.checkedRadioButtonId
        val selectedDrinkId = binding.rdgDrink.checkedRadioButtonId
        val selectedDesertId = binding.rdgDessert.checkedRadioButtonId

        val missingSelections = mutableListOf<String>()

        if (selectedMealId == -1) {
            missingSelections.add("主餐")
        }
        if (selectedDrinkId == -1) {
            missingSelections.add("飲料")
        }
        if (selectedDesertId == -1) {
            missingSelections.add("副餐")
        }

        if (missingSelections.isNotEmpty()) {
            val missingSelectionsText = missingSelections.joinToString(", ")
            AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("請選擇 ${missingSelectionsText}")
                .setPositiveButton("確定") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        } else {
            val selectedMealText = findViewById<RadioButton>(selectedMealId).text.toString()
            val selectedDrinkText = findViewById<RadioButton>(selectedDrinkId).text.toString()
            val selectedDesertText = findViewById<RadioButton>(selectedDesertId).text.toString()

            val selectedAddText = StringBuilder()
            if (binding.chbOriginal.isChecked) {
                selectedAddText.append(binding.chbOriginal.text)
                if (binding.chbSpicy.isChecked) {
                    selectedAddText.append(", ")
                }
            }
            if (binding.chbSpicy.isChecked) {
                selectedAddText.append(binding.chbSpicy.text)
            }

            AlertDialog.Builder(this)

                .setTitle("確認點餐")
                .setMessage("總金額為 $$totalAmount, 是否確認餐點?" )
                .setPositiveButton("是") { dialog, _ ->
                    val intent = Intent()
                    intent.putExtra("TOTAL_AMOUNT", totalAmount)
                    intent.putExtra("SELECTED_MEAL", selectedMealText)
                    intent.putExtra("SELECTED_DRINK", selectedDrinkText)
                    intent.putExtra("SELECTED_DESERT", selectedDesertText)

                    // 检查是否有加点内容，若有则传递，否则不传递
                    if (selectedAddText.isNotEmpty()) {
                        intent.putExtra("SELECTED_ADD", selectedAddText.toString())
                    }

                    setResult(Activity.RESULT_OK, intent)
                    finish()

                    // 在用户按下 "是" 后且有加点内容时清除前次的加点内容
                    if (selectedAddText.isNotEmpty()) {
                        binding.chbOriginal.isChecked = false
                        binding.chbSpicy.isChecked = false
                    }
                }
                .setNegativeButton("否") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

}

