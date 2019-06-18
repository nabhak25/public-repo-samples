package com.example.ktodosample.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.ktodosample.databinding.SingleTaskItemBinding
import com.example.ktodosample.interfaces.TaskListener
import com.example.ktodosample.model.TaskModel

class TasksAdapter(private val taskList: ArrayList<TaskModel>, private val listener: TaskListener) : RecyclerView.Adapter<TasksAdapter.TasksViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TasksViewHolder {
        val inflater = LayoutInflater.from(p0.context)
        val binding = SingleTaskItemBinding.inflate(inflater)
        return TasksViewHolder(binding)
    }

    override fun getItemCount(): Int = taskList.size

    override fun onBindViewHolder(holder: TasksViewHolder, pos: Int) = holder.bind(taskList[pos])

    inner class TasksViewHolder(private val binding: SingleTaskItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TaskModel) {
            binding.taskModel = item
            binding.listener = listener
            binding.executePendingBindings()
        }
    }
}