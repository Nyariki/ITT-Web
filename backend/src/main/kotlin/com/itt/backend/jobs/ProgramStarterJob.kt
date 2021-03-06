/*
 * Copyright (c) 2020. Robert Mayore
 */

package com.itt.backend.jobs

import com.itt.backend.IttBackendApplication
import com.itt.backend.addSeconds
import com.itt.backend.service.EventsService
import com.itt.backend.stopJobs
import org.quartz.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

/**
 * Program starter job.
 *
 * This job will start the ITT program.
 * Will run every time as defined by the cron schedule
 * @see JobsConfiguration#programStartCronSchedule
 */
@Component
class ProgramStarterJob : Job {

    @Autowired
    private val scheduler: Scheduler? = null

    @Autowired
    private val eventsService: EventsService? = null

    companion object{
        const val jobGroup = "ITT-QUARTZ_GROUP: "
    }



    @Throws(JobExecutionException::class)
    override fun execute(arg0: JobExecutionContext?) {
        println(jobGroup + "Task Tracker Job Started")

        //Clear all events from db table
        eventsService?.deleteAllEvents()

        //Launch jobs afresh
        launchProgramTimeJob()
        launchStartServerJob()
        launchStopServerJob()
        launchReportServersRunningJob()

    }

    /**
     * Launch start server job
     */
    private fun launchProgramTimeJob() {
        val job: JobDetail = JobBuilder.newJob(ProgramTimeJob::class.java)
                .withIdentity(ProgramTimeJob::class.java.simpleName, jobGroup)
                .build()

        val trigger = TriggerBuilder.newTrigger()
                .withIdentity("${ProgramTimeJob::class.java.simpleName}Trigger", jobGroup)
                .startAt(Date().addSeconds(1))
                .withPriority(ProgramTimeJob.priority)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(ProgramTimeJob.interval)
                        .repeatForever())
                .forJob(ProgramTimeJob::class.java.simpleName, jobGroup)
                .build()

        scheduler?.scheduleJob(job, trigger)
    }

    /**
     * Launch start server job
     */
    private fun launchStartServerJob() {
        val job: JobDetail = JobBuilder.newJob(StartServerJob::class.java)
                .withIdentity(StartServerJob::class.java.simpleName, jobGroup)
                .build()

        val trigger = TriggerBuilder.newTrigger()
                .withIdentity("${StartServerJob::class.java.simpleName}Trigger", jobGroup)
                .startAt(Date().addSeconds(StartServerJob.interval))
                .withPriority(StartServerJob.priority)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(StartServerJob.interval)
                        .repeatForever())
                .forJob(StartServerJob::class.java.simpleName, jobGroup)
                .build()

        scheduler?.scheduleJob(job, trigger)
    }

    /**
     * Launch stop server job
     */
    private fun launchStopServerJob() {
        val job: JobDetail = JobBuilder.newJob(StopServerJob::class.java)
                .withIdentity(StopServerJob::class.java.simpleName, jobGroup)
                .build()

        val trigger = TriggerBuilder.newTrigger()
                .withIdentity("${StopServerJob::class.java.simpleName}Trigger", jobGroup)
                .startAt(Date().addSeconds(StopServerJob.interval))
                .withPriority(StopServerJob.priority)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(StopServerJob.interval)
                        .repeatForever())
                .forJob(StopServerJob::class.java.simpleName, jobGroup)
                .build()

        scheduler?.scheduleJob(job, trigger)
    }

    /**
     * Launch report servers job
     */
    private fun launchReportServersRunningJob() {
        val job: JobDetail = JobBuilder.newJob(ReportServersRunningJob::class.java)
                .withIdentity(ReportServersRunningJob::class.java.simpleName, jobGroup)
                .build()

        val trigger = TriggerBuilder.newTrigger()
                .withIdentity("${ReportServersRunningJob::class.java.simpleName}Trigger", jobGroup)
                .startAt(Date().addSeconds(ReportServersRunningJob.interval))
                .withPriority(ReportServersRunningJob.priority)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(ReportServersRunningJob.interval)
                        .repeatForever())
                .forJob(ReportServersRunningJob::class.java.simpleName, jobGroup)
                .build()

        scheduler?.scheduleJob(job, trigger)
    }
}