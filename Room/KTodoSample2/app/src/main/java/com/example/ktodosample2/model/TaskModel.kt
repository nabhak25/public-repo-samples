package com.example.ktodosample2.model

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.example.ktodosample2.BR
import com.example.ktodosample2.entity.Task
import java.io.Serializable

data class TaskModel(var task: Task): Serializable, BaseObservable() {
    var description: String = ""
        @Bindable
        set(value) {
            field = value
            notifyPropertyChanged(BR.taskModel)
        }
}