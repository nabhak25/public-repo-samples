package com.example.nabha.grocerylist.utils

object ItemIdGenerator {
    var currentId = 0

    fun getNextId() = currentId++
}