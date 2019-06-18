package com.example.ktodosample.interfaces

import com.example.ktodosample.model.TaskModel

interface TaskListener {

    fun onTaskSelected(task: TaskModel)
}