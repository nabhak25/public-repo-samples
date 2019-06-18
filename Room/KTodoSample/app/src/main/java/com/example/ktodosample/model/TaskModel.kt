package com.example.ktodosample.model

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.example.ktodosample.BR
import com.example.ktodosample.entity.Task
import java.io.Serializable

data class TaskModel(val task: Task) : Serializable, BaseObservable() {

    var taskTitle: String = ""
        @Bindable
        set(value) {
            field = value
            notifyPropertyChanged(BR.taskModel)
        }

    var taskDescription: String = ""
        @Bindable
        set(value) {
            field = value
            notifyPropertyChanged(BR.taskModel)
        }

    var finishBy: String = ""
        @Bindable
        set(value) {
            field = value
            notifyPropertyChanged(BR.taskModel)
        }

    var finished: Boolean = false
        @Bindable
        set(value) {
            field = value
            notifyPropertyChanged(BR.taskModel)
        }

    var color: Int = 0
        @Bindable
        set(value) {
            field = value
            notifyPropertyChanged(BR.taskModel)
        }
}