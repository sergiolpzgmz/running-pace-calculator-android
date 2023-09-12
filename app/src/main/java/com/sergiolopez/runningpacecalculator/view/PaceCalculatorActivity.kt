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
        * Listens for changes in the value of the range slider (`rsDistance`) and
        * updates the selected distance value in the view model class accordingly.
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

    /**
     * Sets the values of time attributes in the ViewModel class based on user input.
     *
     * This function retrieves user input from the hours, minutes, and seconds input fields
     * in the GUI and assigns these values to corresponding attributes
     * in the ViewModel class (`dataViewModel`). If any of the input fields are empty, a default
     * value of 0.0 is assigned to the respective time attribute in the ViewModel.
     */
    private fun setTimeValues() {
        if (etHours.text.toString() != "") dataViewModel.setHours(
            etHours.text.toString().toDouble()
        )
        else dataViewModel.setMinutes(0.0)

        if (etMinutes.text.toString() != "") dataViewModel.setMinutes(
            etMinutes.text.toString().toDouble()
        )
        else dataViewModel.setMinutes(0.0)

        if (etSeconds.text.toString() != "") dataViewModel.setSeconds(
            etSeconds.text.toString().toDouble()
        )
        else dataViewModel.setSeconds(0.0)
    }

    /**
     * Initializes the User Interface (UI) to display the user-selected distance.
     *
     * This function observes changes in the user-selected distance value in the ViewModel (`dataViewModel`)
     * and updates the GUI element (`tvCustomDistance`) to display the selected distance in kilometers.
     */
    private fun initUI() {
        dataViewModel.distanceSelected.observe(this, Observer { distance ->
            tvCustomDistance.text = "$distance KM"
        })
    }

    /**
     * Navigates to the result activity with user-inserted data.
     *
     * This function checks if a distance has been selected.
     * If a distance is not selected, it displays a toast message prompting the user to select a distance.
     * If a distance is selected, it creates an intent to navigate to the result activity
     * (`ResultPaceCalculatorActivity`) and includes user-inserted data as extras in the intent bundle.
     *
     * @param resultPace The calculated result of the run pace.
     */
    private fun navigateToResultActivity(resultPeace: MutableLiveData<Double>) {
        if (dataViewModel.distanceSelected.value == 0) {
            Toast.makeText(this, "Please, select a distance", Toast.LENGTH_SHORT).show()
        } else {
            val intent = Intent(this, ResultPaceCalculatorActivity::class.java)
            startActivity(intent.putExtras(bundle(resultPeace)))
        }
    }

    /**
     * Encapsulates all the data collected on this screen into a bundle.
     *
     * This function creates a `Bundle` containing various pieces of user-inserted data,
     * including the calculated run pace, selected distance, hours, minutes, and seconds.
     *
     * @param resultPace The calculated result of the run pace.
     * @return An encapsulated bundle containing the collected data.
     */
    private fun bundle(resultPeace: MutableLiveData<Double>): Bundle {
        val bundle = Bundle()
        bundle.putFloat(KEY_RESULT, resultPeace.value!!.toFloat())
        bundle.putInt(KEY_DISTANCE, dataViewModel.distanceSelected.value!!.toInt())
        bundle.putString(KEY_HOURS, dataViewModel.hours.value.toString())
        bundle.putString(KEY_MINUTES, dataViewModel.minutes.value.toString())
        bundle.putString(KEY_SECONDS, dataViewModel.seconds.value.toString())

        return bundle
    }
}