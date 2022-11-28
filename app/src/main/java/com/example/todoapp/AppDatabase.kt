package com.example.todoapp

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todoapp.Task
import com.example.todoapp.TaskDao

@Database(entities = [Task::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}