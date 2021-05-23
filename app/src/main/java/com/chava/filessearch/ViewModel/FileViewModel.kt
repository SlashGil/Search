package com.chava.filessearch.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.chava.filessearch.Adapter.WordsAdapter
import com.chava.filessearch.Models.File
import com.chava.filessearch.Models.Word

/**
 * FileViewModel is part of the presentation path in this app that is an MVVM
 * Here are contained all the calls to File Model also the adapter for the RecyclerView
 */
class FileViewModel: ViewModel() {
    private var adapter: WordsAdapter? = null
    var file:File? = null

    /**
     * analyzeFile is the function for create a File and attach the File and his words to the adapter
     * @author Salvador Romero Gil
     * @date 22/05/2021
     * @param path Path is where our file is
     */
    fun analyzeFile(path:String){
        file = File(path)
        adapter = WordsAdapter(file!!.words)
    }

    /**
     * filter is a function for enable user to make some changes is the manner of displaying the information of the RecyclerView
     * @param int Is an Integer that comes from position of AlertDialog's array also as array is already written we can make the filter without any issue
     * @author Salvador Romero Gil
     * @date 21/05/2021
     */
    fun filter(int: Int){
        when(int){
            0->{
                adapter!!.filter(file!!.words)
            }
            1 -> {
                adapter!!.filter(file!!.words.sortedBy { it.slug })
            }
            2 -> {
                adapter!!.filter(file!!.words.sortedBy { it.match })
            }
            3-> {
                adapter!!.filter(file!!.words.sortedByDescending { it.match })
            }
        }
    }

    /**
     * filterQuery is the function for call the adapter's filter function that change the dataSet of RecyclerView
     */
    fun filterQuery(query:List<Word>){
        adapter!!.filter(query)
    }
    fun getAdapter():WordsAdapter{
        return adapter!!
    }

}