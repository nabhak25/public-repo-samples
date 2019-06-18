package com.example.ktodosample2.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import com.example.ktodosample2.R
import com.example.ktodosample2.databinding.SingleTaskItemBinding
import com.example.ktodosample2.entity.Task
import com.example.ktodosample2.interfaces.TaskListener
import com.example.ktodosample2.model.TaskModel
import kotlinx.android.synthetic.main.single_task_item.view.*

class TaskAdapter(
    private val taskList: List<Task>,
    private val listener: TaskListener
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(p0.context)
        val binding = SingleTaskItemBinding.inflate(inflater)
        return TaskViewHolder(binding)

    }

    override fun getItemCount() = taskList.size

    override fun onBindViewHolder(p0: TaskViewHolder, p1: Int) = p0.bind(taskList[p1])


    inner class TaskViewHolder(private val binding: SingleTaskItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.taskModel = TaskModel(task)
            binding.listener = listener
        }

        init {
            binding.root.imageMenu.setOnClickListener {
                val popupMenu = PopupMenu(binding.root.context, binding.root.imageMenu)
                popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)

                popupMenu.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.update -> {
                            listener.onUpdate(TaskModel(taskList[adapterPosition]))
                        }

                        R.id.delete -> {
                            listener.onDelete(TaskModel(taskList[adapterPosition]))
                        }
                    }
                    true
                }
                popupMenu.show()
            }
        }
    }
}