package com.chava.filessearch.models

import java.util.*
import java.util.regex.Pattern

class Word (){
    var match: Int = 0
    var slug: String = ""
    var points = arrayOf(",",".",";",":","-"," ","`","´","'","\"","(",")")
    constructor(value:String,match:Int) : this() {
        val regex = Regex("[^a-z,^A-Z,^0-9]")
        //Looking for regex that is only able to have numbers and letters
        if(value.isNotEmpty() && !regex.matches(value)){
            var text: String? = ""
            points.forEach {
                if(value.contains(it)) { text = value.replace(it,"") }
            }
            slug = createSlug(text!!)
            this.match = match
        }
        if(regex.matches(value) && value.isNotEmpty()){
            val text = value.replace(regex,"")
            slug = createSlug(text)
            this.match = match
        }
    }
    fun checkString(str: String): Boolean{
        val txt = createSlug(str)
        return txt == slug
    }
    fun createSlug(str: String): String{
        val st = str.lowercase(Locale.getDefault())
        return st.replaceFirstChar{ if(it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()}
    }
}