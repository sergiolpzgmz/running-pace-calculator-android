package com.sergiolopez.runningpacecalculator.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sergiolopez.runningpacecalculator.databinding.ActivityPaceCalculatorBinding
import com.sergiolopez.runningpacecalculator.view.result.ResultPaceCalculatorActivity

class PaceCalculatorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaceCalculatorBinding
    private lateinit var dataViewModel: PaceCalculatorViewModel

    // Keys associated with the values sent in the bundle
    companion object {
        const val KEY_DISTANCE = "DISTANCE"
        const val KEY_HOURS = "HOURS"
        const val KEY_MINUTES = "MINUTES"
        const val KEY_SECONDS = "SECONDS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaceCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataViewModel = ViewModelProvider(this)[PaceCalculatorViewModel::class.java]

        initListeners()
        initUI()
    }

    private fun initListeners() {
        /*
        * Listens for changes in the value of the range slider (`rsDistance`) and
        * updates the selected distance value in the view model class accordingly.
        */
        binding.rsDistance.addOnChangeListener { _, value, _ ->
            dataViewModel.setDistanceSelected(value.toInt())
        }

        binding.btnDistance5.setOnClickListener {
            dataViewModel.setDistanceSelected(5)
        }

        binding.btnDistance10.setOnClickListener {
            dataViewModel.setDistanceSelected(10)
        }

        binding.btnDistance21.setOnClickListener {
            dataViewModel.setDistanceSelected(21)
        }

        binding.btnDistance42.setOnClickListener {
            dataViewModel.setDistanceSelected(42)
        }

        binding.btnCalculate.setOnClickListener {
            setTimeValues()
            navigateToResultActivity()
        }
    }

     //Sets the values of time attributes in the ViewModel class based on user input.
    private fun setTimeValues() {
        if (binding.etHours.text.toString() != "") dataViewModel.setHours(
            binding.etHours.text.toString().toDouble()
        )
        else dataViewModel.setMinutes(0.0)

        if (binding.etMinutes.text.toString() != "") dataViewModel.setMinutes(
            binding.etMinutes.text.toString().toDouble()
        )
        else dataViewModel.setMinutes(0.0)

        if (binding.etSeconds.text.toString() != "") dataViewModel.setSeconds(
            binding.etSeconds.text.toString().toDouble()
        )
        else dataViewModel.setSeconds(0.0)
    }

    /**
     * Initializes the User Interface (UI) to display the user-selected distance.
     *
     * This function observes changes in the user-selected distance value in the ViewModel
     * and updates the GUI element to display the selected distance in kilometers.
     */
    private fun initUI() {
        dataViewModel.distanceSelected.observe(this, Observer { distance ->
            binding.tvCustomDistance.text = "$distance KM"
        })
    }


    /**
     * Navigates to the result activity with user-inserted data.
     *
     * This function checks if a distance has been selected.
     * If a distance is not selected, it displays a toast message prompting the user to select a distance.
     * If a distance is selected, it creates an intent to navigate to the result activity
     * and includes user-inserted data as extras in the intent bundle.
     *
     */
    private fun navigateToResultActivity() {
        if (dataViewModel.distanceSelected.value?.toInt() == 0) {
            Toast.makeText(this, "Please, select a distance", Toast.LENGTH_SHORT).show()
        } else {
            val intent = Intent(this, ResultPaceCalculatorActivity::class.java)
            startActivity(intent.putExtras(bundle()))
        }
    }

    /**
     * Encapsulates all the data collected on this screen into a bundle.
     *
     * This function creates a `Bundle` containing various pieces of user-inserted data.
     *
     * @return an encapsulated bundle containing the collected data.
     */
    private fun bundle(): Bundle {
        val bundle = Bundle()
        bundle.putInt(KEY_DISTANCE, dataViewModel.distanceSelected.value!!.toInt())
        bundle.putDouble(KEY_HOURS, dataViewModel.hours.value!!)
        bundle.putDouble(KEY_MINUTES, dataViewModel.minutes.value!!)
        bundle.putDouble(KEY_SECONDS, dataViewModel.seconds.value!!)

        return bundle
    }
}