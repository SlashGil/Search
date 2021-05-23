package com.chava.filessearch.Models
import java.io.File

/**
 * File Class is the principal Model from this application, since File we can make several changes in strings and call Word Model for obtain an slug ready to check after
 * @author Salvador Romero Gil
 * @date 20/05/2021
 * @param path Path is an string where path to txt file is saved
 */
open class File(val path: String) {
    var words: List<Word> = readFile()
    protected fun readFile():List<Word>{
        val list = mutableListOf<Word>()
        val strings = mutableListOf<String>()
        val text = File(path).readLines() //Read the entire file by lines
        text.forEach { i->
            i.split(" ").forEach { //Delimited by every space that is a word
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
         var nums = strings.groupingBy {
             it
         }.eachCount().filter { it.value > 0 }
        nums.forEach { s, i ->
            list.add(Word(s,i))
        }
        list.removeIf { it.slug.isEmpty() || it.slug.isBlank()}
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
    fun checkWord(txt: String,strings: MutableList<String>) {
        val word = Word(txt.replace("[^\\p{IsAlphabetic}\\p{IsDigit}]", ""), 1)
        strings.add(word.slug)
    }
}