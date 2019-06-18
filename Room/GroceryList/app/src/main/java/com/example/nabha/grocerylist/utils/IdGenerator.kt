package com.example.nabha.grocerylist.utils

object IdGenerator {
    var currentId = 0

    fun getNextId() = currentId++
}