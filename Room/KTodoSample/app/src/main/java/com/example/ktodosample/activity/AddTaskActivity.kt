package com.example.ktodosample.activity

import android.app.Activity
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.ktodosample.R
import com.example.ktodosample.database.DatabaseClient
import com.example.ktodosample.databinding.ActivityAddTaskBinding
import com.example.ktodosample.entity.Task
import com.example.ktodosample.model.TaskModel
import kotlinx.android.synthetic.main.activity_add_task.*
import java.util.*

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding
    private lateinit var taskModel: TaskModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_task)
        binding.activity = this
        taskModel = TaskModel(Task())
        binding.taskModel = taskModel
    }

    fun saveTask(view: View) {
        if (taskModel.taskTitle.isEmpty() || taskModel.taskTitle.isBlank()) {
            editTextTask.error = "Task Required"
            editTextTask.requestFocus()
            return
        }

        if (taskModel.taskDescription.isEmpty() || taskModel.taskDescription.isBlank()) {
            editTextTask.error = "Description Required"
            editTextTask.requestFocus()
            return
        }

        if (taskModel.finishBy.isEmpty() || taskModel.finishBy.isBlank()) {
            editTextTask.error = "Finish By Required"
            editTextTask.requestFocus()
            return
        }

        class SaveTask : AsyncTask<Void, Void, Void>() {


            override fun doInBackground(vararg p0: Void?): Void? {
                val newTask = Task()
                newTask.task = taskModel.taskTitle
                newTask.description = taskModel.taskDescription
                newTask.finishBy = taskModel.finishBy
                newTask.finished = false
                newTask.color = getRandomColor()

                DatabaseClient.getInstance(applicationContext).getAppDatabase()
                    .taskDao()
                    .insert(task = newTask)

                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                setResult(Activity.RESULT_OK)
                finish()
                Toast.makeText(applicationContext, "Saved", Toast.LENGTH_LONG).show()
            }

        }

        SaveTask().execute()
    }

    private fun getRandomColor(): Int {
        val random = Random()
        return Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256))
    }
}
