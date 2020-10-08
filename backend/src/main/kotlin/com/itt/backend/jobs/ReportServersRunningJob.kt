/*
 * Copyright (c) 2020. Robert Mayore
 */

package com.itt.backend.jobs

import com.itt.backend.IttBackendApplication
import com.itt.backend.programTimeFormat
import com.itt.backend.service.EventsService
import com.itt.data.model.Event
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*


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

    @Autowired
    private val eventsService: EventsService? = null

    companion object {
        val priority = 3
            get() = 100 - field //Convert priority to quartz priority

        const val interval = 50
    }

    @Throws(JobExecutionException::class)
    override fun execute(arg0: JobExecutionContext?) {
        val n =IttBackendApplication.serversRunning

        //save event to db
        eventsService?.addEvent(Event().apply {
            this.type = 3
            this.color = IttBackendApplication.reportJobColor.get()
            this.message = "Report $n servers running"
            this.time = programTimeFormat.format(IttBackendApplication.programTime.get().time)
        })
        //log
        println(ProgramStarterJob.jobGroup + "$n servers running")
    }
}