package com.example.lab2

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.view.*
import android.widget.BaseAdapter
import android.widget.TextView
import java.util.*
import kotlin.collections.ArrayList
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import android.content.DialogInterface
import android.widget.EditText
import com.example.lab2.Employee
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class ListAdapter(var context: Context, var objects: ArrayList<Employee>) : BaseAdapter()
{
    var lInflater: LayoutInflater? = null

    init {
        lInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
    }

    override fun getCount(): Int
    {
        return objects.size
    }

    override fun getItem(position: Int): Any {
        return objects[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view: View? = convertView
        if (view == null) {
            view = lInflater?.inflate(R.layout.simple_list_item_1, parent, false)
        }

        val employee = getEmployee(position)

        val popupMenu = PopupMenu(context, view!!)
        popupMenu.inflate(R.menu.popupmenu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.info -> {
                    CreateInfoMessage(employee)
                    true
                }
                R.id.delete -> {
                    deleteEmployee(employee)
                    true
                }
                R.id.refactor -> {
                    CreateRefactorMessage(position)
                    true
                }
                else -> false
            }
        }

        val text = view.findViewById(R.id.label) as TextView
        text.setText(employee.fullName)

        text.setOnLongClickListener {
            popupMenu.show()
            true
        }
        return view
    }

    fun getEmployee(position: Int): Employee {
        return getItem(position) as Employee
    }

    fun deleteEmployee(employee: Employee)
    {
        objects.remove(employee)
    }
    fun CreateInfoMessage(employee: Employee)
    {
        val textView = TextView(context)

        with(textView) {
            textView.text = "Info about employee:"
            textView.textSize = 18.0F
            textView.setTypeface(null, Typeface.BOLD)
            textView.gravity = Gravity.CENTER
        }

        var infoString = "Full name:" + employee.fullName + "\n"
        infoString+= "Department number:" + employee.departmentNumber.toString() + "\n"
        infoString+= "Post:" + employee.post + "\n"
        infoString+= "First date:"+ employee.firstDate.day.toString() + "." + (employee.firstDate.month+1).toString() + "." + (employee.firstDate.year + 1900).toString()

        val builder = AlertDialog.Builder(context)
        builder
            .setCustomTitle(textView)
            .setMessage(infoString)
            .setPositiveButton("Ok") {
                    dialog, id ->  dialog.cancel()
            }
        builder.create()
        builder.show()
    }
    fun CreateRefactorMessage(position: Int)
    {
        val builder = AlertDialog.Builder(context)
        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.add_layout, null)

        (view.findViewById(R.id.editTextFullName) as EditText).setText(objects[position].fullName)
        (view.findViewById(R.id.editTextDepartmentNumber) as EditText).setText(objects[position].departmentNumber.toString())
        (view.findViewById(R.id.editTextPost) as EditText).setText(objects[position].post)
        (view.findViewById(R.id.editTextFirstDateDay) as EditText).setText(objects[position].firstDate.day.toString())
        (view.findViewById(R.id.editTextFirstDateMonth) as EditText).setText((objects[position].firstDate.month+1).toString())
        (view.findViewById(R.id.editTextFirstDateYear) as EditText).setText((objects[position].firstDate.year+1900).toString())

        builder.setView(view)
        builder.setPositiveButton("Ok") { dialog, id ->
            RefactorEmployee(view, position)
            dialog.cancel()
        }
        builder.create()
        builder.show()
    }
    fun RefactorEmployee(view:View, position: Int)
    {
        val fullName: String = (view.findViewById(R.id.editTextFullName) as EditText).text.toString()
        val departmentNumber: Int = (view.findViewById(R.id.editTextDepartmentNumber) as EditText).text.toString().toInt()
        val post: String = (view.findViewById(R.id.editTextPost) as EditText).text.toString()
        val day: Int = (view.findViewById(R.id.editTextFirstDateDay) as EditText).text.toString().toInt()
        val month: Int = (view.findViewById(R.id.editTextFirstDateMonth) as EditText).text.toString().toInt()
        val year: Int = (view.findViewById(R.id.editTextFirstDateYear) as EditText).text.toString().toInt()
        val firstDate: Date = Date((year-1900),(month-1),day) as Date
        objects[position].fullName = fullName
        objects[position].departmentNumber = departmentNumber
        objects[position].post = post
        objects[position].firstDate = firstDate
    }
}
