package com.sergiolopez.runningpacecalculator.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.slider.RangeSlider
import com.sergiolopez.runningpacecalculator.databinding.ActivityPaceCalculatorBinding
import com.sergiolopez.runningpacecalculator.viewModel.DataViewModel

class PaceCalculatorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaceCalculatorBinding
    private lateinit var dataViewModel: DataViewModel

    private lateinit var rsDistance: RangeSlider
    private lateinit var tvCustomDistance: TextView
    private lateinit var btnDistance5: ExtendedFloatingActionButton
    private lateinit var btnDistance10: ExtendedFloatingActionButton
    private lateinit var btnDistance21: ExtendedFloatingActionButton
    private lateinit var btnDistance42: ExtendedFloatingActionButton
    private lateinit var etHours: EditText
    private lateinit var etMinutes: EditText
    private lateinit var etSeconds: EditText
    private lateinit var btnCalculate: Button

    // Keys associated with the values sent in the bundle
    companion object {
        const val KEY_RESULT = "PACE_RESULT"
        const val KEY_DISTANCE = "DISTANCE"
        const val KEY_HOURS = "HOURS"
        const val KEY_MINUTES = "MINUTES"
        const val KEY_SECONDS = "SECONDS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaceCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataViewModel = ViewModelProvider(this)[DataViewModel::class.java]

        initComponents()
        initListeners()
        initUI()
    }

    // Initialises the gui components
    private fun initComponents() {
        rsDistance = binding.rsDistance
        tvCustomDistance = binding.tvCustomDistance
        btnDistance5 = binding.btnDistance5
        btnDistance10 = binding.btnDistance10
        btnDistance21 = binding.btnDistance21
        btnDistance42 = binding.btnDistance42
        etHours = binding.etHours
        etMinutes = binding.etMinutes
        etSeconds = binding.etSeconds
        btnCalculate = binding.btnCalculate
    }


    private fun initListeners() {
        /*
        * Gets the value of the range slider
        * and assigns it to the corresponding attribute in the view model class
        */
        rsDistance.addOnChangeListener { _, value, _ ->
            dataViewModel.setDistanceSelected(value.toInt())
        }

        btnDistance5.setOnClickListener {
            dataViewModel.setDistanceSelected(5)
        }

        btnDistance10.setOnClickListener {
            dataViewModel.setDistanceSelected(10)
        }

        btnDistance21.setOnClickListener {
            dataViewModel.setDistanceSelected(21)
        }

        btnDistance42.setOnClickListener {
            dataViewModel.setDistanceSelected(42)
        }

        btnCalculate.setOnClickListener {
            setTimeValues()
            dataViewModel.calculatePace()
            navigateToResultActivity(dataViewModel.resultPace)
        }
    }

    // Sets the value of the time attributes in the modelview class
    private fun setTimeValues() {
        if (etHours.text.toString() != "") dataViewModel.setHours(
            etHours.text.toString().toFloat()
        )

        if (etMinutes.text.toString() != "") dataViewModel.setMinutes(
            etMinutes.text.toString().toFloat()
        )
        else dataViewModel.setMinutes(0f)
        if (etSeconds.text.toString() != "") dataViewModel.setSeconds(
            etSeconds.text.toString().toFloat()
        )
        else dataViewModel.setSeconds(0f)
    }

    // Displays the user-selected distance
    private fun initUI() {
        dataViewModel.distanceSelected.observe(this, Observer { distance ->
            tvCustomDistance.text = "$distance KM"
        })
    }

    /**
     * Navigates to the result activity with all user inserted data
     *
     * @param resultPeace the calculation result of run pace
     */
    private fun navigateToResultActivity(resultPeace: MutableLiveData<Float>) {
        if (dataViewModel.distanceSelected.value == 0) {
            Toast.makeText(this, "Please, select a distance", Toast.LENGTH_SHORT).show()
        } else {
            val intent = Intent(this, ResultPaceCalculatorActivity::class.java)
            startActivity(intent.putExtras(bundle(resultPeace)))
        }
    }

    /**
     * Encapsulates all the data we get on this screen
     *
     * @param resultPeace the calculation result of run pace
     *
     * @return encapsulated data
     */
    private fun bundle(resultPeace: MutableLiveData<Float>): Bundle {
        val bundle = Bundle()
        bundle.putFloat(KEY_RESULT, resultPeace.value!!.toFloat())
        bundle.putInt(KEY_DISTANCE, dataViewModel.distanceSelected.value!!.toInt())
        bundle.putString(KEY_HOURS, dataViewModel.hours.value.toString())
        bundle.putString(KEY_MINUTES, dataViewModel.minutes.value.toString())
        bundle.putString(KEY_SECONDS, dataViewModel.seconds.value.toString())

        return bundle
    }
}