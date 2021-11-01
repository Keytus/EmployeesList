package com.example.lab2

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import java.util.*


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
                    notifyDataSetChanged()
                    true
                }
                R.id.refactor -> {
                    CreateRefactorMessage(position)
                    true
                }
                R.id.add ->{
                    CreateAddMessage()
                    true
                }
                R.id.department ->{
                    CreateSortedListMessage(position)
                    true
                }
                else -> false
            }
        }

        val text = view.findViewById(R.id.label) as TextView
        text.setText(employee.toString())

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
            textView.text = context.getString(R.string.add_header_string)
            textView.textSize = 18.0F
            textView.setTypeface(null, Typeface.BOLD)
            textView.gravity = Gravity.CENTER
        }

        var infoString = "Full name:" + employee.fullName + "\n"
        infoString+= "Department number:" + employee.departmentNumber.toString() + "\n"
        infoString+= "Post:" + employee.post + "\n"
        infoString+= "First date:"+ employee.firstDate.getDate().toString() + "." + (employee.firstDate.month+1).toString() + "." + (employee.firstDate.year + 1900).toString()


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
            notifyDataSetChanged()
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

    fun CreateAddMessage()
    {
        val builder = AlertDialog.Builder(context)
        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.add_layout, null)
        builder.setView(view)
        builder.setPositiveButton("OK"
        ) { dialog, id ->
            CreateEmployee(view)
            notifyDataSetChanged()
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

        val firstDate = Date((year-1900),(month-1),day)

        objects.add(Employee(fullName, departmentNumber, post, firstDate))
    }
    fun CreateSortedListMessage(position: Int)
    {
        val builder = AlertDialog.Builder(context)
        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.sorted_list_layout, null)

        val needEmployees = (objects.filter { it.departmentNumber == objects[position].departmentNumber }) as ArrayList<Employee>
        needEmployees.sortWith(compareBy { it.firstDate })
        val listAdapter = SortedListAdapter(context, needEmployees)
        val employeesList: ListView = view.findViewById(R.id.sortedEmployeesList)
        employeesList.setAdapter(listAdapter as android.widget.ListAdapter?)

        builder.setView(view)
        builder.setPositiveButton("OK"
        ) { dialog, id ->
            dialog.cancel()
        }
        builder.create()
        builder.show()
    }
}
