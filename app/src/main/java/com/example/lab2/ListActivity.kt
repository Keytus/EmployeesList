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
import kotlin.properties.Delegates




class ListActivity : AppCompatActivity() {

    var employees = arrayListOf<Employee>()
    var case:Int = 0
    private lateinit var fullName:String
    private var departmentNumber by Delegates.notNull<Number>()
    private lateinit var post:String
    private var year by Delegates.notNull<Int>()
    private var month by Delegates.notNull<Int>()
    private var day by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val newStr:String= "Victor Meranov\n" + "24\n" + "brother\n" + "45\n" + "4\n" + "9\n"

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

        add_button.setOnClickListener {
            CreateAddMessage()
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

    private fun add(Component:String){
        case++
        when(case){
            1 -> fullName = Component
            2 -> departmentNumber = Component.toInt()
            3 -> post = Component
            4 -> year = Component.toInt()
            5 -> month = Component.toInt()
            6 -> {day = Component.toInt();
                case = 0;
                employees.add(
                    Employee(fullName, departmentNumber,post,  Date(year, month, day))
                )}
        }
    }

    fun CreateAddMessage()
    {
        val builder = AlertDialog.Builder(this)
        val inflater: LayoutInflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.add_layout, null)
        builder.setView(view)
        builder.setPositiveButton("OK"
        ) { dialog, id ->
            CreateEmployee(view)
            dialog.cancel()
        }
        builder.create()
        builder.show()
    }

    fun CreateEmployee(view: View)
    {
        val fullName: String = (view.findViewById(R.id.editTextFullName) as EditText).text.toString()
        val departmentNumber: Int = (view.findViewById(R.id.editTextDepartmentNumber) as EditText).text.toString().toInt()
        val post: String = (view.findViewById(R.id.editTextPost) as EditText).text.toString()
        val day: Int = (view.findViewById(R.id.editTextFirstDateDay) as EditText).text.toString().toInt()
        val month: Int = (view.findViewById(R.id.editTextFirstDateMonth) as EditText).text.toString().toInt()
        val year: Int = (view.findViewById(R.id.editTextFirstDateYear) as EditText).text.toString().toInt()
        val firstDate: Date = Date((year-1900),(month-1),day) as Date
        employees.add(
            Employee(fullName,
                departmentNumber,
                post,
                firstDate))
    }
}