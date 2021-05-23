package com.chava.filessearch.activities

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chava.filessearch.Adapter.WordsAdapter
import com.chava.filessearch.R
import com.chava.filessearch.databinding.ActivityMainBinding
import com.chava.filessearch.models.File
import com.chava.filessearch.models.GridSpacingItemDecoration
import com.chava.filessearch.models.Word
import com.chava.filessearch.models.getFilePath
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    val READ_STORAGE_RQ = 101
    val WRITE_STORAGE_RQ = 102
    val PICK_FILE = 1
    var items = arrayOf("Posición","Alfabetico","Apariciones")
    var file: File? = null
    lateinit var binding: ActivityMainBinding
    var adapter: WordsAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(newText)
                return false
            }
        })
        binding.skeletonRV.layoutManager = GridLayoutManager(this@MainActivity,2,
            RecyclerView.VERTICAL,false)
        binding.skeletonRV.addItemDecoration(GridSpacingItemDecoration(2,35,true))
        checkForPremissions(Manifest.permission.READ_EXTERNAL_STORAGE,"Read Permission",READ_STORAGE_RQ)
        checkForPremissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,"Write Permission",WRITE_STORAGE_RQ)
        binding.filters.setOnClickListener {
            deployFilters()
        }
        binding.searchFile.setOnClickListener{
            openFile()
        }
        binding.changeFile.setOnClickListener {
            openFile()
        }
    }

    private fun deployFilters() {
        val builder = MaterialAlertDialogBuilder(this@MainActivity)
        builder.setTitle("Filtros")
        builder.setSingleChoiceItems(
            items,1, DialogInterface.OnClickListener { dialogInterface, which ->
                for(i in items.indices){
                    when(which == i){
                        true->{
                            filterBy(items[i])
                            dialogInterface.dismiss()
                        }
                    }
                }
            }
        )
        var alert: androidx.appcompat.app.AlertDialog = builder.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()

    }

    private fun filterBy(s: String) {
        when(s){
            "Posicion"->{
                adapter!!.filter(file!!.words)
            }
            "Alfabetico"->{
                adapter!!.filter(file!!.words.sortedBy { it.slug.toString() })
            }
            "Apariciones"->{
                adapter!!.filter(file!!.words.sortedBy { it.match })
            }
        }

    }

    private fun filter(txt: String){
        val newList = mutableListOf<Word>()
        for(word in file!!.words){
            if (word.slug.contains(txt))
                newList.add(word)
        }
        if(newList.isEmpty()){
            Snackbar.make(this@MainActivity,binding.root,"I don´t have words like your search",Snackbar.LENGTH_LONG).show()
        } else {
            adapter!!.filter(newList.toList())
        }
    }

    fun openFile(){
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "text/plain"
        }
        startActivityForResult(intent,PICK_FILE)
    }
    private fun checkForPremissions(permission: String, name: String, requestCode: Int){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            when{
                ContextCompat.checkSelfPermission(applicationContext,permission) == PackageManager.PERMISSION_GRANTED -> {
                    Snackbar.make(this@MainActivity,binding.filters,"$name already is granted Thank you!",Snackbar.LENGTH_LONG).setBackgroundTint(getColor(
                        R.color.dark_blue
                    )).setTextColor(getColor(R.color.pure_light)).show()
                }
                shouldShowRequestPermissionRationale(permission)->showDialog(permission,name,requestCode)
                else -> ActivityCompat.requestPermissions(this,arrayOf(permission),requestCode)
            }
        }
    }

    private fun showDialog(permission: String,name: String,requestCode: Int){
        val builder = AlertDialog.Builder(this)

        builder.apply {
            setMessage("Permission to access your $name is required to use this app")
            setTitle("Permission Required")
            setPositiveButton("Ok"){ dialog, which ->
                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission),requestCode)
            }
        }
        val dialog = builder.create()
        dialog.show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        fun innerCheck(name: String){
            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                Snackbar.make(this@MainActivity,binding.filters,"$name permission refused",Snackbar.LENGTH_LONG).setBackgroundTint(getColor(
                    R.color.dark_blue
                )).setTextColor(getColor(R.color.pure_light)).show()
            } else {
                Snackbar.make(this@MainActivity,binding.filters,"$name permission already granted", Snackbar.LENGTH_LONG).setBackgroundTint(getColor(
                    R.color.dark_blue
                )).setTextColor(getColor(R.color.pure_light)).show()
            }
        }

        when(requestCode){
            READ_STORAGE_RQ -> innerCheck("Read Storage")
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(PICK_FILE==requestCode && resultCode== RESULT_OK){
            file = File(data!!.getFilePath(this@MainActivity))
            Snackbar.make(this@MainActivity,binding.filters,file!!.path,Snackbar.LENGTH_LONG).setBackgroundTint(getColor(
                R.color.dark_blue
            )).setTextColor(getColor(R.color.pure_light)).show()
            Snackbar.make(this@MainActivity,binding.filters,"El archivo contiene " + file!!.words.size + " palabras diferentes !",Snackbar.LENGTH_LONG).setBackgroundTint(getColor(
                R.color.dark_blue
            )).setTextColor(getColor(R.color.pure_light)).show()
            binding.skeletonRV.visibility = View.VISIBLE
            binding.searchFile.visibility = View.GONE
            binding.changeFile.visibility = View.VISIBLE
            binding.skeletonRV.startLoading()
            binding.fileName.text = "Archivo: " + data!!.data!!.lastPathSegment!!.split('/')[2].toString()
            var num = 0
            for (word in file!!.words) {
                num+=word.match
            }
            binding.totalWords.text = "Palabras: $num"
            adapter = WordsAdapter(file!!.words)
            binding.skeletonRV.adapter = adapter
            binding.skeletonRV.stopLoading()
        }
        super.onActivityResult(requestCode, resultCode, data)

    }
}