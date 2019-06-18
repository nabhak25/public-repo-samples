package com.example.ktodosample.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity
class Task: Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "task")
    var task: String = ""

    @ColumnInfo(name = "description")
    var description: String = ""

    @ColumnInfo(name = "finish_by")
    var finishBy: String = ""

    @ColumnInfo(name = "finished")
    var finished: Boolean = false

    @ColumnInfo(name = "color")
    var color: Int = 0
}