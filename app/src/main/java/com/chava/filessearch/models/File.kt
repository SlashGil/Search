package com.chava.filessearch.models
import java.io.File
import java.io.BufferedReader
open class File(val path: String) {
    var words: List<Word> = readFile()

    protected fun readFile():List<Word>{
        val list = mutableListOf<Word>()
        val reader: BufferedReader = File(path).bufferedReader() //Create a bufferedReader for enable the file read
        val string = reader.use { it.readText() } //Read the entire file
        val text = string.split(' ')
        text.forEach {
            if (it.length>1){
                val word = Word(it,1)
                if(list.contains(word)){
                    word.matches++
                }
                else{
                    list.add(word)
                }
            }
        }
        return list.toList()
    }
}