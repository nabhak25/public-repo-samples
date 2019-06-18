package com.example.ktodosample.activity

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.ktodosample.R
import com.example.ktodosample.adapter.TasksAdapter
import com.example.ktodosample.database.DatabaseClient
import com.example.ktodosample.databinding.ActivityMainBinding
import com.example.ktodosample.entity.Task
import com.example.ktodosample.interfaces.TaskListener
import com.example.ktodosample.model.TaskModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), TaskListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.ktodosample.R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.activity = this

        recyclerview_tasks.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        getTasks()
    }

    fun addTask(view: View) {
        val addTaskIntent = Intent(this, AddTaskActivity::class.java)
        startActivityForResult(addTaskIntent, ADD_TASK_REQUEST)
    }

    private fun getTasks() {
        class GetTasks : AsyncTask<Void, Void, List<Task>>() {

            override fun doInBackground(vararg p0: Void?): List<Task> {
                return DatabaseClient.getInstance(applicationContext)
                    .getAppDatabase()
                    .taskDao()
                    .getAll()
            }

            override fun onPostExecute(result: List<Task>?) {
                super.onPostExecute(result)
                val tasksAdapter = result?.let { TasksAdapter(convertToTaskModel(it), this@MainActivity) }
                recyclerview_tasks.adapter = tasksAdapter
            }
        }

        GetTasks().execute()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            getTasks()
        }
    }


    override fun onTaskSelected(task: TaskModel) {
        val intent = Intent(this, UpdateTaskActivity::class.java)
        intent.putExtra("task", task)
        startActivityForResult(intent, UPDATE_TASK_REQUEST)
    }

    companion object {
        const val ADD_TASK_REQUEST = 100
        const val UPDATE_TASK_REQUEST = 101
    }

    private fun convertToTaskModel(list: List<Task>): ArrayList<TaskModel> {
        val taskModels = ArrayList<TaskModel>()
        for (task in list) {
            taskModels.add(TaskModel(task))
        }
        return taskModels
    }
}
