package com.kroger.classapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kroger.classapp.model.Task
import com.kroger.classapp.repository.TaskRepository
import com.kroger.classapp.utils.Resource

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val taskRepository = TaskRepository(application)

    fun getTaskList() = taskRepository.getTaskList()

    fun insertTask(task: Task): MutableLiveData<Resource<Long>> {
        return taskRepository.insertTask(task)
    }

    fun deleteTask(task: Task): MutableLiveData<Resource<Int>> {
        return taskRepository.deleteTask(task)
    }

    fun deleteTaskUsingId(taskId: String): MutableLiveData<Resource<Int>> {
        return taskRepository.deleteTaskUsingId(taskId)
    }

    fun updateTask(task: Task): MutableLiveData<Resource<Int>> {
        return taskRepository.updateTask(task)
    }

    fun updateTaskPaticularField(taskId: String,title:String,description:String): MutableLiveData<Resource<Int>> {
        return taskRepository.updateTaskPaticularField(taskId, title, description)
    }
}