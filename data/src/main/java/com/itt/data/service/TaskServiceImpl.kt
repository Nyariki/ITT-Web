/*
 * Copyright (c) 2020. Robert Mayore
 */

package com.itt.data.service

import com.itt.data.dao.TasksDao
import com.itt.data.model.TaskExample
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TaskServiceImpl : TaskService {

    @Autowired
    private val tasksDao: TasksDao? = null

    override fun deleteAllTasks() {
        val taskExample = TaskExample()
        taskExample.createCriteria().andIdIsNotNull() //will include all DB records
        tasksDao?.deleteByExample(taskExample)
    }
}