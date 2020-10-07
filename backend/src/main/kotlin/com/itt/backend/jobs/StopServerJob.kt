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
 * Stop server job.
 *
 * This job will stop n servers,
 * n = random integer between 5 and k
 * k = total number of servers running
 * interval = 40 seconds
 * priority = 2
 *
 */
@Component
class StopServerJob : Job {

    companion object {
        val priority = 2
            get() = 100 - field //Convert priority to quartz priority

        const val interval = 40
    }

    @Throws(JobExecutionException::class)
    override fun execute(arg0: JobExecutionContext?) {
        val n = (5..IttBackendApplication.serversRunning.get()).random()
        println(ProgramStarterJob.jobGroup + "Stopped $n servers")
        IttBackendApplication.serversRunning.addAndGet(-n)
    }
}