package com.example.lab2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ListAdapter
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates




class ListActivity : AppCompatActivity() {

    var employees = arrayListOf<Employee>()
    var case:Int = 0
    private lateinit var fullName:String
    private var departmentNumber by Delegates.notNull<Number>()
    private lateinit var post:String
    private lateinit var dateString:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)


        val newStr:String= "Victor Meranov\n" + "24\n" + "brother\n" + Date(45,6,11).toString()+"\n"

        val fileName2 = "Records.txt"
        var file = File("/data/user/0/com.example.lab2/files/LET/",fileName2)
        var fileExists = file.exists()


        if(!fileExists) {
            val path = this.getFilesDir()
            val letDirectory = File(path, "LET")
            letDirectory.mkdirs()
            val file = File(letDirectory, fileName2)
            FileOutputStream(file).use {
                it.write(newStr.toByteArray())
            }
        }

        val bufferedReader = file.bufferedReader()
        bufferedReader.useLines { lines -> lines.forEach { add(it) } }


        back_button.setOnClickListener {
            val intent = Intent(this@ListActivity, MainActivity::class.java)
            startActivity(intent)
        }

        val listAdapter = ListAdapter(this, employees)

        val employeesList: ListView = findViewById(R.id.employeeList)

        employeesList.setAdapter(listAdapter as ListAdapter?)
    }

    override fun onStop() {
        File("/data/user/0/com.example.lab2/files/LET/Records.txt").bufferedWriter().use { out ->
            employees.forEach { out.write(it.toFile()) }
        }
        super.onStop()
    }

    override fun onDestroy() {
        File("/data/user/0/com.example.lab2/files/LET/Records.txt").bufferedWriter().use { out ->
            employees.forEach { out.write(it.toFile()) }
        }
        super.onDestroy()
    }

    private fun add(Component:String){
        case++
        when(case){
            1 -> fullName = Component
            2 -> departmentNumber = Component.toInt()
            3 -> post = Component
            4 -> {dateString = Component
                case = 0;
                employees.add(
                    Employee(fullName, departmentNumber,post,  Date(dateString))
                )}
        }
    }
}