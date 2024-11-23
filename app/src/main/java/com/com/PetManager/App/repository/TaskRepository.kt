package com.kroger.classapp.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.coding.meet.todo_app.database.TaskDatabase
import com.kroger.classapp.model.Task
import com.kroger.classapp.utils.Resource
import com.kroger.classapp.utils.Resource.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch

class TaskRepository(application: Application) {

    private val taskDao = TaskDatabase.getInstance(application).taskDao


    fun getTaskList() = flow {
        emit(Resource.Loading())
        try {
            val result = taskDao.getTaskList()
            emit(Resource.Success(result))
        }catch (e:Exception){
            emit(Error(e.message.toString()))
        }
    }


    fun insertTask(task: Task) = MutableLiveData<Resource<Long>>().apply {
        postValue(Resource.Loading())
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val result = taskDao.insertTask(task)
                postValue(Success(result))
            }
        }catch (e: Exception){
            postValue(Error(e.message.toString()))
        }
    }


    fun deleteTask(task: Task) = MutableLiveData<Resource<Int>>().apply {
        postValue(Resource.Loading())
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val result = taskDao.deleteTask(task)
                postValue(Success(result))
            }
        }catch (e: Exception){
            postValue(Error(e.message.toString()))
        }
    }

    fun deleteTaskUsingId(taskId: String) = MutableLiveData<Resource<Int>>().apply {
        postValue(Resource.Loading())
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val result = taskDao.deleteTaskUsingId(taskId)
                postValue(Resource.Success(result))
            }
        }catch (e: Exception){
            postValue(Error(e.message.toString()))
        }
    }


    fun updateTask(task: Task) = MutableLiveData<Resource<Int>>().apply {
        postValue(Resource.Loading())
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val result = taskDao.updateTask(task)
                postValue(Resource.Success(result))
            }
        }catch (e: Exception){
            postValue(Error(e.message.toString()))
        }
    }
    fun updateTaskPaticularField(taskId: String,title:String,description:String) = MutableLiveData<Resource<Int>>().apply {
        postValue(Resource.Loading())
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val result = taskDao.updateTaskPaticularField(taskId, title, description)
                postValue(Resource.Success(result))
            }
        }catch (e: Exception){
            postValue(Error(e.message.toString()))
        }
    }

}