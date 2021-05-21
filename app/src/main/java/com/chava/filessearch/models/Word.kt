package com.chava.filessearch.models

import java.util.*

class Word (value: String, var matches: Int){
    val slug = value.replaceFirstChar{ if(it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()}
    val variants:List<String> = getVariants()
    var string = value
    @JvmName("getVariants1")
    fun getVariants(): List<String> {
        val array = listOf(
            slug.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
            slug.replaceFirstChar { it.lowercase(Locale.getDefault()) }, "$slug?", "$slug!", "$slug,","$slug:","$slug.","$slug;","'$slug'")
        return array.toList()
    }
}