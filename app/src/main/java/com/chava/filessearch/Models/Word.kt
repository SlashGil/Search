package com.chava.filessearch.Models

import java.util.*

/**
 * Word Model is the most minimum abstraction of this problem
 * Here are contained the slug and the matches of the word
 * @author Salvador Romero Gil
 * @date 20/05/2021
 */
class Word (){
    var match: Int = 0
    var slug: String = ""
    constructor(value:String,match:Int) : this() {
        //Looking for regex that is only able to have numbers and letters
        if(value.isNotEmpty()){
            slug = createSlug(value)
            this.match = match
        }
    }

    /**
     * checkString have the only task of Review if a string is already an slug
     * @param str The string to review
     * @return Boolean The value of review
     * @author Salvador Romero Gil
     * @date 23/05/2021
     */
    fun checkString(str: String): Boolean{
        val txt = createSlug(str)
        return txt == slug
    }

    /**
     * createSlug is used for return an String were his characters are only letters and numbers
     * @see It's not performing so well, so I need to make fixes in this functions but actually performs a little bit well.
     * @param str The word from file
     * @return String with capital
     * @author Salvador Romero Gil
     * @date 23/05/2021
     */
    fun createSlug(str: String): String{
        var txt: String = ""
        val st = str.lowercase(Locale.getDefault()).forEach {
            txt += it.toString().replace("[^\\p{IsAlphabetic}\\p{IsDigit}]","")
        }
        return txt.replaceFirstChar{ if(it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()}
    }
}