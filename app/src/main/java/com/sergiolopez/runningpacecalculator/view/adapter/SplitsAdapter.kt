package com.sergiolopez.runningpacecalculator.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sergiolopez.runningpacecalculator.R

class SplitsAdapter(private val splitTimesList:MutableList<Float>):RecyclerView.Adapter<SplitsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SplitsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SplitsViewHolder(layoutInflater.inflate(R.layout.item_split, parent, false))
    }

    override fun getItemCount(): Int {
        return splitTimesList.size
    }

    override fun onBindViewHolder(holder: SplitsViewHolder, position: Int) {
        holder.render(splitTimesList[position])
    }
}