package com.chava.filessearch.models

import java.util.*
import java.util.regex.Pattern

class Word (){
    var match: Int = 0
    var slug: String = ""
    constructor(value:String,match:Int) : this() {
        val regex = Regex("[^A-Za-z0-9]")
        if(value.isNotEmpty() && !regex.matches(value)){
            slug = value.replaceFirstChar{ if(it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()}
            this.match = match
        }
        if(regex.matches(value) && value.isNotEmpty()){
            val text = value.replace(regex,"")
            slug = text.replaceFirstChar{ if(it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()}
            this.match = match
        }
    }
}