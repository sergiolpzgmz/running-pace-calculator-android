package com.sergiolopez.runningpacecalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.slider.RangeSlider
import com.sergiolopez.runningpacecalculator.databinding.ActivityPaceCalculatorBinding


class PaceCalculatorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaceCalculatorBinding

    private var distanceSelected: Int = 0;

    private var currentValue: Int = 0

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

        initComponents();
        initListeners();

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
        rsDistance.addOnChangeListener { _, value, _ ->
            currentValue = value.toInt()
            distanceSelected = currentValue
            tvCustomDistance.text = "$currentValue KM"

        }

        btnDistance5.setOnClickListener {
            tvCustomDistanceValue(5)
        }

        btnDistance10.setOnClickListener {
            tvCustomDistanceValue(10)
        }

        btnDistance21.setOnClickListener {
            tvCustomDistanceValue(21)
        }

        btnDistance42.setOnClickListener {
            tvCustomDistanceValue(42)
        }

        btnCalculate.setOnClickListener {
            val resultPeace = calculatePeace()
            navigateToResultActivity(resultPeace)
        }
    }

    private fun navigateToResultActivity(resultPeace: Float) {
        if (distanceSelected == 0) {
            Toast.makeText(this, "Please, select a distance", Toast.LENGTH_SHORT).show()
        } else {
            val intent = Intent(this, ResultPaceCalculatorActivity::class.java)
            startActivity(intent.putExtras(bundle(resultPeace)))
        }
    }

    private fun bundle(resultPeace: Float): Bundle {
        val bundle = Bundle()
        bundle.putFloat(KEY_RESULT, resultPeace)
        bundle.putInt(KEY_DISTANCE, distanceSelected)
        bundle.putString(KEY_HOURS, etHours.text.toString())
        bundle.putString(KEY_MINUTES, etMinutes.text.toString())
        bundle.putString(KEY_SECONDS, etSeconds.text.toString())

        return bundle

    }

    private fun calculatePeace(): Float {

        var hoursValue = etHours.text.toString()
        var minutesValue = etMinutes.text.toString()
        var secondsValue = etSeconds.text.toString()

        if (hoursValue == "") hoursValue = "0"
        if (minutesValue == "") minutesValue = "0"
        if (secondsValue == "") secondsValue = "0"

        // Convert all units to minutes
        val hours: Float = hoursValue.toFloat() * 60
        val seconds: Float = secondsValue.toFloat() / 60

        return (hours + minutesValue.toFloat() + seconds) / distanceSelected
    }

    private fun tvCustomDistanceValue(value: Int) {
        tvCustomDistance.text = "$value KM"
        distanceSelected = value
    }


}