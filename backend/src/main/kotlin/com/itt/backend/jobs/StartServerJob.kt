/*
 * Copyright (c) 2020. Robert Mayore
 */

package com.itt.backend.jobs

import com.itt.backend.IttBackendApplication
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException
import org.springframework.stereotype.Component


/**
 * Start server job.
 *
 * This job will start n servers,
 * n = random integer between 10 and 20
 * interval = 30 seconds
 * priority = 1
 *
 */
@Component
class StartServerJob : Job {

    companion object {
        val priority = 1
            get() = 100 - field //Convert priority to quartz priority

        const val interval = 30
    }

    @Throws(JobExecutionException::class)
    override fun execute(arg0: JobExecutionContext?) {
        val n = (10..20).random()
        println(ProgramStarterJob.jobGroup + "Started $n servers")
        IttBackendApplication.serversRunning.addAndGet(n)
    }
}