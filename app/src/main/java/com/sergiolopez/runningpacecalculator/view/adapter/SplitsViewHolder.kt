package com.sergiolopez.runningpacecalculator.view.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sergiolopez.runningpacecalculator.R
import com.sergiolopez.runningpacecalculator.model.RunPaceModel

class SplitsViewHolder(view: View):RecyclerView.ViewHolder(view) {
    val name = view.findViewById<TextView>(R.id.tvName)

    fun render (){
        name.text="test"
    }
}