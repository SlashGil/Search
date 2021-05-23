package com.chava.filessearch.models
import java.io.File
import java.io.BufferedReader
open class File(val path: String) {
    var words: List<Word> = readFile()
    protected fun readFile():List<Word>{
        val list = mutableListOf<Word>()
        val strings = mutableListOf<String>()
        val text = File(path).readLines() //Read the entire file
        text.forEach { i->
            i.split(" ").forEach {
                checkWord(it,strings)
            }
        }
        return getWords(strings,list)
    }

    private fun getWords(strings: MutableList<String>, list: MutableList<Word>): List<Word> {
         var nums = strings.groupingBy { it }.eachCount().filter { it.value > 1 }
        nums.forEach { s, i ->
            list.add(Word(s,i))
        }
        list.removeIf { it.slug.isEmpty() }
        return list.toList()
    }

    fun checkWord(txt: String,strings: MutableList<String>){
        val regex = Regex("[^a-z,^A-Z,^0-9]")
        if(regex.matches(txt)){
            val word = Word(txt.replace(regex,""),1)
            strings.add(word.slug)
        }
        else{
            val word = Word(txt,1)
            strings.add(word.slug)
        }


    }
}