package com.chava.filessearch

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chava.filessearch.models.File
import com.chava.filessearch.models.RealPathUtil.getRealPath
import com.chava.filessearch.models.getFilePath
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    var fileName: MaterialTextView? = null
    var words:MaterialTextView? = null
    val PICK_FILE = 1
    var filters: MaterialButton? = null
    var searchFile: MaterialButton? = null
    var file: File? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        filters = findViewById<MaterialButton>(R.id.filters)
        searchFile = findViewById<MaterialButton>(R.id.searchFile)
        filters!!.setOnClickListener {
            deployFilters()
        }
        searchFile!!.setOnClickListener{
            openFile()

        }
    }

    private fun deployFilters() {
        TODO("Not yet implemented")
    }

    fun openFile(){
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "text/plain"
        }
        startActivityForResult(intent,PICK_FILE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(PICK_FILE==requestCode && resultCode== RESULT_OK){
            file = File(data!!.getFilePath(this@MainActivity))
            Toast.makeText(this@MainActivity,file!!.path,Toast.LENGTH_LONG).show()
        }
        super.onActivityResult(requestCode, resultCode, data)

    }
}