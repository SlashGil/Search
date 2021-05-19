package com.chava.filessearch.models
import java.io.File
import java.io.BufferedReader
class File(path: String) {
    val path = path
    lateinit var words: List<Word>

    private fun readFile():List<Word>{
        var array = mutableListOf<Word>()
        val reader: BufferedReader = File(path).bufferedReader() //Create a bufferedReader for enable the file read
        val string = reader.use { it.readText() } //Read the entire file
        var list = string.split(' ')
        list.forEach {
            if (it.length>1 && !array.contains(it)){
                var word = Word(it,1)
                word.getMatches()
                array.add(word)
            }
        }
    }
}