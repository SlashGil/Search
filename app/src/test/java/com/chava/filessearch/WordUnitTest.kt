package com.chava.filessearch

import com.chava.filessearch.models.Word
import org.junit.Test

import org.junit.Assert.*

/**
 * Class for the unit testing of Word Class
 * This Unit Testing process try to ensure 3 types of word breakpoint
 * 1.- Word that only have special characters like '.',',' etc...
 * 2.- Word that have text and also an special character
 * 3.- Word that is composed only by 1 character but this is an accepted character
 *
 * What is an accepted character in this scope?
 * An accepted character is any letter no matter if is Upper or Lowercase and also numbers
 *
 * @author Salvador Romero Gil
 * @date 21/05/2021
 */
class WordUnitTest {
    /**
     * Test for ensure that strings for one character and if this is an special character is not allowed
     * @param str Is a string composed only by special character
     * @author Salvador Romero Gil
     * @date 21/05/2021
     */
    @Test
    fun onlyAcceptStrings(str: String = "?"){
        val word = Word(str,0)
        assertEquals(null,word)
    }

    /**
     * Test for ensure that if a word contains an special character, this is deleted when Word object is created
     * @param str An string that has an special character inside
     * @author Salvador Romero Gil
     * @date 21/05/2021
     */
    @Test
    fun notSpecialCharacters(str: String = "Hi!"){
        val word = Word(str,1)
        assertEquals("Hi",word.slug)
    }

    /**
     * Test for ensure that single character words are already allowed
     * @param str A single character string
     * @author Salvador Romero Gil
     * @date 21/05/2021
     */
    @Test
    fun singleChar(str:String = "a"){
        val word = Word(str,1)
        assertEquals("A",word.slug)
    }
}