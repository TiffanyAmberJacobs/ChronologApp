package com.example.chronologapp

class AppData {
    companion object {
        var categories: ArrayList<Category> = ArrayList()
        var arrTimeSheet = ArrayList<timeSheet>()                     //global arrays to safe the data for each object
        var dailyGoal : ArrayList<hourGoals> = ArrayList()
    }
}