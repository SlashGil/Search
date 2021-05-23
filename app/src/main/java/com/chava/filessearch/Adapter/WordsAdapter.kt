package com.chava.filessearch.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chava.filessearch.R
import com.chava.filessearch.models.Word
import com.google.android.material.textview.MaterialTextView

class WordsAdapter(var dataSet:List<Word>): RecyclerView.Adapter<WordsAdapter.ViewHolder>()  {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val word: MaterialTextView = view.findViewById(R.id.wordTitle)
        val times: MaterialTextView = view.findViewById(R.id.foundedAt)
    }
    fun filter(data: List<Word>){
        this.dataSet = data
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row, parent, false)
        var layoutParams: RecyclerView.LayoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        view.layoutParams = layoutParams
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val time = dataSet[position].match
        holder.word.text = dataSet[position].slug
        holder.times.text = " $time veces"
    }

    override fun getItemCount(): Int {
       return dataSet.size
    }
}