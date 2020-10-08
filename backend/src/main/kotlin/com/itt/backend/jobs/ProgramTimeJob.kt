/*
 * Copyright (c) 2020. Robert Mayore
 */

package com.itt.backend.jobs

import com.itt.backend.IttBackendApplication
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException
import org.springframework.stereotype.Component
import java.util.*


/**
 * Manage program time job.
 *
 * This job will manage the program time
 *
 */
@Component
class ProgramTimeJob : Job {

    companion object {
        val priority = 0
            get() = 100 - field //Convert priority to quartz priority

        const val interval = 1
    }

    @Throws(JobExecutionException::class)
    override fun execute(arg0: JobExecutionContext?) {
        IttBackendApplication.programTime.getAndUpdate {
            it.apply {
                this.set(Calendar.SECOND, this.get(Calendar.SECOND) + 1)
            }
        }
    }
}