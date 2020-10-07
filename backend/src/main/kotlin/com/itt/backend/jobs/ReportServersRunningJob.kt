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
 * n = random integer between 10 and 20 inclusive
 * interval = 50 seconds
 * priority = 3
 *
 */
@Component
class ReportServersRunningJob : Job {

    companion object {
        val priority = 3
            get() = 100 - field //Convert priority to quartz priority

        const val interval = 50
    }

    @Throws(JobExecutionException::class)
    override fun execute(arg0: JobExecutionContext?) {
        println(ProgramStarterJob.jobGroup + "${IttBackendApplication.serversRunning} servers running")
    }
}