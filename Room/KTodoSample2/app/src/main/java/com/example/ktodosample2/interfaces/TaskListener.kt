package com.example.ktodosample2.interfaces

import com.example.ktodosample2.model.TaskModel

interface TaskListener {

    fun onTaskSelected(taskModel: TaskModel)

    fun onUpdate(taskModel: TaskModel)

    fun onDelete(taskModel: TaskModel)
}