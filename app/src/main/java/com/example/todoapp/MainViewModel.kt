package com.example.todoapp

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val taskDao: TaskDao): ViewModel() {
    var title by mutableStateOf("")
    var description by mutableStateOf("")

    var isShowDialog by mutableStateOf(false)

    val tasks = taskDao.loadAllTasks().distinctUntilChanged()
    // ここでIDを保持
    private var editingTask: Task? = null
    val idEditing: Boolean
    // nullであればtaskの新規作成ちゅ
    get() = editingTask != null

    fun setEditingTask(task: Task) {
        editingTask = task
        title = task.title
        description = task.description

    }

    fun createTask() {
        viewModelScope.launch {
            val newTask = Task(title = title, description = description)
            taskDao.insertTask(newTask)
            Log.d(MainViewModel::class.java.simpleName, "success create task")
        }

    }
    fun deleteTask(task: Task){
        viewModelScope.launch {
            taskDao.deleteTask(task)
        }
    }
    fun updateTask(){
        // editingTaskがnullの場合には実行されない
        editingTask?.let { task ->
            viewModelScope.launch {
                task.title = title
                task.description = description
                taskDao.updateTask(task)
        }

        }
    }
    fun resetProperties(){
        editingTask = null
        title = ""
        description = ""
    }
}