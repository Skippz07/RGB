//Name: Ulysses Carbajal
//CWID: 887269900
//Email: UlyssesC@csu.fullerton.edu
package edu.fz.cs411.rgb

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.SeekBar
import android.graphics.Color
import android.widget.Button
import android.widget.Switch
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.Log
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView


class MainActivity : AppCompatActivity() {
    private lateinit var redSeekBar: SeekBar
    private lateinit var greenSeekBar: SeekBar
    private lateinit var blueSeekBar: SeekBar
    private lateinit var redValue: EditText
    private lateinit var greenValue: EditText
    private lateinit var blueValue: EditText
    private lateinit var resetButton: Button
    private val viewModel: ColorViewModel by lazy { ViewModelProvider(this).get(ColorViewModel::class.java) }
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var redSwitch: Switch
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    lateinit var greenSwitch: Switch
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var blueSwitch: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Bind views
        redSeekBar = findViewById(R.id.redSeekBar)
        redValue = findViewById(R.id.redValue)
        greenSeekBar = findViewById(R.id.greenSeekBar)
        greenValue = findViewById(R.id.greenValue)
        blueSeekBar = findViewById(R.id.blueSeekBar)
        blueValue = findViewById(R.id.blueValue)
        redSwitch = findViewById(R.id.redSwitch)
        greenSwitch = findViewById(R.id.greenSwitch)
        blueSwitch = findViewById(R.id.blueSwitch)
        resetButton = findViewById(R.id.resetButton)
        viewModel.redValue.observe(this, { _ -> updateColor() })
        viewModel.greenValue.observe(this, { _ -> updateColor() })
        viewModel.blueValue.observe(this, { _ -> updateColor() })


        // Initialize values based on ViewModel
        redSeekBar.progress = viewModel.redValue.value!!
        redValue.setText((viewModel.redValue.value!! / 100.00).toString())

        greenSeekBar.progress = viewModel.greenValue.value!!
        greenValue.setText((viewModel.greenValue.value!! / 100.00).toString())

        blueSeekBar.progress = viewModel.blueValue.value!!
        blueValue.setText((viewModel.blueValue.value!! / 100.00).toString())

        initValuesFromViewModel()

        addTextChangedListener()

        // Set listeners for SeekBars
        setSeekBarListeners()

        // Handle switch changes
        handleSwitchChanges()

        // Reset button functionality
        resetButton.setOnClickListener {
            viewModel.resetValues()
            initValuesFromViewModel()
            updateColor()
        }
    }

    private fun setSeekBarListeners() {
        redSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.redValue.value = progress
                redValue.setText((progress / 100.00).toString())  // Update EditText in real-time
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        greenSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.greenValue.value = progress
                greenValue.setText((progress / 100.00).toString())  // Update EditText in real-time
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        blueSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.blueValue.value = progress
                blueValue.setText((progress / 100.00).toString())  // Update EditText in real-time
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }


    private fun addTextChangedListener() {
        redValue.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val value = s.toString().toDoubleOrNull()
                if (value != null) {
                    when {
                        value > 1.00 -> {
                            viewModel.redValue.value = 100
                            redSeekBar.progress = 100
                            redValue.setText("1.00")
                        }
                        value < 0.00 -> {
                            viewModel.redValue.value = 0
                            redSeekBar.progress = 0
                            redValue.setText("0.00")
                        }
                        else -> {
                            viewModel.redValue.value = (value * 100).toInt()
                            redSeekBar.progress = (value * 100).toInt()
                        }
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
        greenValue.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val value = s.toString().toDoubleOrNull()
                if (value != null) {
                    when {
                        value > 1.00 -> {
                            viewModel.greenValue.value = 100
                            greenSeekBar.progress = 100
                            greenValue.setText("1.00")
                        }
                        value < 0.00 -> {
                            viewModel.greenValue.value = 0
                            greenSeekBar.progress = 0
                            greenValue.setText("0.00")
                        }
                        else -> {
                            viewModel.greenValue.value = (value * 100).toInt()
                            greenSeekBar.progress = (value * 100).toInt()
                        }
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        blueValue.addTextChangedListener(object : TextWatcher {
            @SuppressLint("SetTextI18n")
            override fun afterTextChanged(s: Editable?) {
                val value = s.toString().toDoubleOrNull()
                if (value != null) {
                    when {
                        value > 1.00 -> {
                            viewModel.blueValue.value = 100
                            blueSeekBar.progress = 100
                            blueValue.setText("1.00")
                        }
                        value < 0.00 -> {
                            viewModel.blueValue.value = 0
                            blueSeekBar.progress = 0
                            blueValue.setText("0.00")
                        }
                        else -> {
                            viewModel.blueValue.value = (value * 100).toInt()
                            blueSeekBar.progress = (value * 100).toInt()
                        }
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

    }

    private fun handleSwitchChanges() {
        redSwitch.setOnCheckedChangeListener { _, isChecked ->
            redSeekBar.isEnabled = isChecked
            redValue.isEnabled = isChecked
            if (!isChecked) {
                viewModel.disableRed()
            } else {
                viewModel.enableRed()
                redSeekBar.progress = viewModel.redValue.value!!
                redValue.setText((viewModel.redValue.value!! / 100.00).toString())
            }
        }

        greenSwitch.setOnCheckedChangeListener { _, isChecked ->
            greenSeekBar.isEnabled = isChecked
            greenValue.isEnabled = isChecked
            if (!isChecked) {
                viewModel.disableGreen()
            } else {
                viewModel.enableGreen()
                greenSeekBar.progress = viewModel.greenValue.value!!
                greenValue.setText((viewModel.greenValue.value!! / 100.00).toString())
            }
        }

        blueSwitch.setOnCheckedChangeListener { _, isChecked ->
            blueSeekBar.isEnabled = isChecked
            blueValue.isEnabled = isChecked
            if (!isChecked) {
                viewModel.disableBlue()
            } else {
                viewModel.enableBlue()
                blueSeekBar.progress = viewModel.blueValue.value!!
                blueValue.setText((viewModel.blueValue.value!! / 100.00).toString())
            }
        }
    }



    private fun initValuesFromViewModel() {
        redSeekBar.progress = viewModel.redValue.value!!
        redValue.setText((viewModel.redValue.value!! / 100.00).toString())

        greenSeekBar.progress = viewModel.greenValue.value!!
        greenValue.setText((viewModel.greenValue.value!! / 100.00).toString())

        blueSeekBar.progress = viewModel.blueValue.value!!
        blueValue.setText((viewModel.blueValue.value!! / 100.00).toString())
    }

    private fun updateColor() {
        val red = (viewModel.redValue.value ?: 100) * 2.55
        val green = (viewModel.greenValue.value ?: 100) * 2.55
        val blue = (viewModel.blueValue.value ?: 100) * 2.55

        val color = Color.rgb(red.toInt(), green.toInt(), blue.toInt())
        findViewById<ConstraintLayout>(R.id.colorBox).setBackgroundColor(color)

        // Update hex color in TextView
        val hexColorText = String.format("#%02X%02X%02X", red.toInt(), green.toInt(), blue.toInt())
        val hexColorTextView = findViewById<TextView>(R.id.tvHexColor)
        hexColorTextView.text = hexColorText
        hexColorTextView.setTextColor(color) // Set the TextView color to the computed color

        Log.d("COLOR_UPDATE", "Updated color to: $color")
    }


}

class ColorViewModel : ViewModel() {
    val redValue = MutableLiveData<Int>().apply { value = 0 }
    val greenValue = MutableLiveData<Int>().apply { value = 0 }
    val blueValue = MutableLiveData<Int>().apply { value = 0 }

    private var previousRedValue = 0
    private var previousGreenValue = 0
    private var previousBlueValue = 0

    fun disableRed() {
        previousRedValue = redValue.value!!
        redValue.value = 0
    }
    fun enableRed() {
        redValue.value = previousRedValue
    }
    fun disableGreen() {
        previousGreenValue = greenValue.value!!
        greenValue.value = 0
    }
    fun enableGreen() {
        greenValue.value = previousGreenValue
    }
    fun disableBlue() {
        previousBlueValue = blueValue.value!!
        blueValue.value = 0
    }
    fun enableBlue() {
        blueValue.value = previousBlueValue
    }
    fun resetValues() {
        redValue.value = 0
        greenValue.value = 0
        blueValue.value = 0
    }
}
