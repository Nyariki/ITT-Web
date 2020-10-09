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

    @Autowired
    private val eventsService: EventsService? = null

    companion object {
        val priority = 2
            get() = 100 - field //Convert priority to quartz priority

        const val interval = 40
    }

    @Throws(JobExecutionException::class)
    override fun execute(arg0: JobExecutionContext?) {
        val n = (5..IttBackendApplication.serversRunning.get()).random()
        IttBackendApplication.serversRunning.addAndGet(-n)

        //save event to db
        eventsService?.addEvent(Event().apply {
            this.type = 2
            this.color = IttBackendApplication.stopJobColor.get()
            this.message = "Stop $n servers"
            this.time = programTimeFormat.format(IttBackendApplication.programTime.get().time)
        })

        //log
        println(ProgramStarterJob.jobGroup + "Stopped $n servers")
    }
}