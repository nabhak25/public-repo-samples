package com.example.ktodosample2.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(
    foreignKeys =
    [ForeignKey(
        entity = Task::class, parentColumns = ["id"], childColumns = ["taskId"],
        onDelete = ForeignKey.CASCADE
    )]
)
class Item : Serializable {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "taskId")
    var taskId: Int = 0

    @ColumnInfo(name = "itemDescription")
    var itemDescription: String = ""

}