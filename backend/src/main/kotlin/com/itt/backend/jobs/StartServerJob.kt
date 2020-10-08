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
 * n = random integer between 10 and 20
 * interval = 30 seconds
 * priority = 1
 *
 */
@Component
class StartServerJob : Job {

    @Autowired
    private val eventsService: EventsService? = null

    companion object {
        val priority = 1
            get() = 100 - field //Convert priority to quartz priority

        const val interval = 30
    }

    @Throws(JobExecutionException::class)
    override fun execute(arg0: JobExecutionContext?) {
        val n = (10..20).random()
        IttBackendApplication.serversRunning.addAndGet(n)

        //save event to db
        eventsService?.addEvent(Event().apply {
            this.type = 1
            this.color = IttBackendApplication.startJobColor.get()
            this.message = "Start $n servers"
            this.time = programTimeFormat.format(IttBackendApplication.programTime.get().time)
        })

        //log
        println(ProgramStarterJob.jobGroup + "Started $n servers")
    }
}