package com.sergiolopez.runningpacecalculator.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sergiolopez.runningpacecalculator.R

class SplitsAdapter(private val splitTimesList: MutableList<String>):RecyclerView.Adapter<SplitsAdapter.SplitsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SplitsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SplitsViewHolder(layoutInflater.inflate(R.layout.item_split, parent, false))
    }

    override fun getItemCount(): Int {
        return splitTimesList.size
    }

    override fun onBindViewHolder(holder: SplitsViewHolder, position: Int) {
        val item = splitTimesList[position]
        holder.name.text = item.toString()
    }
    inner class SplitsViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById<TextView>(R.id.tvName)
    }
}

