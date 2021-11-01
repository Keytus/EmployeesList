package com.example.lab2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.util.ArrayList

class SortedListAdapter(var context: Context, var objects: ArrayList<Employee>) : BaseAdapter() {
    var lInflater: LayoutInflater? = null

    init {
        lInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
    }

    override fun getCount(): Int {
        return objects.size
    }

    override fun getItem(position: Int): Any {
        return objects[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View? = convertView
        if (view == null) {
            view = lInflater?.inflate(R.layout.simple_list_item_1, parent, false)
        }

        val employee = getItem(position)


        val text = view?.findViewById(R.id.label) as TextView
        text.setText(employee.toString())

        return view
    }
}