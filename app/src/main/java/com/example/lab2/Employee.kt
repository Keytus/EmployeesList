package com.example.lab2

import java.util.*



class Employee(var fullName: String, var departmentNumber: Number, var post: String, var firstDate: Date)
{
    override fun toString(): String
    {
        return fullName
    }
    fun toFile(): String {
        var fileString:String = fullName + "\n" + departmentNumber.toString() + "\n"+ post + "\n" + firstDate.year + "\n" + firstDate.month + "\n" + firstDate.day + "\n"
        return fileString
    }
}