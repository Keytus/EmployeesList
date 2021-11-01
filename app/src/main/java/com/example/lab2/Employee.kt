package com.example.lab2

import java.util.*



class Employee(var fullName: String, var departmentNumber: Number, var post: String, var firstDate: Date)
{
    override fun toString(): String
    {
        //return fullName
        var infoString = "Full name:" + fullName + "\n"
        infoString+= "Department number:" + departmentNumber.toString()+ "\n"
        infoString+= "First date:"+ firstDate.getDate().toString() + "." + (firstDate.month+1).toString() + "." + (firstDate.year + 1900).toString()
        return infoString


    }
    fun toFile(): String {
        var fileString:String = fullName + "\n" + departmentNumber.toString() + "\n"+ post + "\n" + firstDate.toString() + "\n"
        return fileString
    }
}