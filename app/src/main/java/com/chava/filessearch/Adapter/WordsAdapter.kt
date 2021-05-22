package com.chava.filessearch.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chava.filessearch.R
import com.chava.filessearch.models.Word
import com.google.android.material.textview.MaterialTextView

class WordsAdapter(var dataSet:Array<Word>): RecyclerView.Adapter<WordsAdapter.ViewHolder>()  {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val word: MaterialTextView = view.findViewById(R.id.wordTitle)
        val times: MaterialTextView = view.findViewById(R.id.foundedAt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val time = dataSet[position].match
        holder.word.text = dataSet[position].slug
        holder.times.text = "Se encontro $time veces"
    }

    override fun getItemCount(): Int {
       return dataSet.size
    }
}