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

    /**
     * Function for try to ensure only one attempt for every string that has singularities between various
     * @author Salvador Romero Gil
     * @date 22/05/2021
     */
    private fun getWords(strings: MutableList<String>, list: MutableList<Word>): List<Word> {
         var nums = strings.groupingBy { it }.eachCount().filter { it.value > 1 }
        nums.forEach { s, i ->
            list.add(Word(s,i))
        }
        list.removeIf { it.slug.isEmpty() }
        return list.toList()
    }

    /**
     * Function created for performing the creation of word and try to ensure that any singular
     * character is in string
     *
     * @author Salvador Romero Gil
     * @date 22/05/2021
     * @param txt The string that we need to check
     * @param strings The List when we will save the slugs
     */
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