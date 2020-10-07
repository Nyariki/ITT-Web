/*
 * Copyright (c) 2020. Robert Mayore
 */

package com.itt.backend.jobs

import org.quartz.*
import org.quartz.impl.StdSchedulerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct


/**
 * Jobs configuration
 *
 * @constructor Create empty Jobs configuration
 */
@Configuration
class JobsConfiguration {

    @Autowired
    private val scheduler: Scheduler? = null

    @Value("\${itt.program.start.schedule}")
    val programStartCronSchedule: String? = null

    private val jobGroup = "ITT-QUARTZ_GROUP"

    @PostConstruct
    fun postConstruct() {
        launchProgramStarterJob()
    }

    /**
     * Launch program starter job
     */
    private fun launchProgramStarterJob() {
        val job: JobDetail = JobBuilder.newJob(ProgramStarterJob::class.java)
                .withIdentity(ProgramStarterJob::class.java.simpleName, jobGroup)
                .build()

        val trigger = TriggerBuilder.newTrigger()
                .withIdentity("${ProgramStarterJob::class.java.simpleName}Trigger", jobGroup)
                .withSchedule(CronScheduleBuilder.cronSchedule(programStartCronSchedule))
                .forJob(ProgramStarterJob::class.java.simpleName, jobGroup)
                .build()

        scheduler?.scheduleJob(job, trigger)
    }
}


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

    @Throws(JobExecutionException::class)
    override fun execute(arg0: JobExecutionContext?) {
        println("Task Tracker Job Started")
        //TODO: 0. Clear DB tasks table
        //TODO: 1. Stop, Launch START job
        //TODO: 2. Stop, Launch STOP job
        //TODO: 3. Stop, Launch REPORT job
    }

    fun stopJob(name: String) {
        val currentlyExecuting = scheduler?.currentlyExecutingJobs

        if (currentlyExecuting != null) {
            for (jobExecutionContext in currentlyExecuting) {
                if (jobExecutionContext.jobDetail.key.name == name) {
                    scheduler?.interrupt(jobExecutionContext.jobDetail.key)
                }
            }
        }
    }
}