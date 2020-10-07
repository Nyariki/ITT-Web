/*
 * Copyright (c) 2020. Robert Mayore
 */

package com.itt.backend.jobs

import org.quartz.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
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


    @PostConstruct
    fun postConstruct() {
        launchProgramStarterJob()
    }

    /**
     * Launch program starter job
     */
    private fun launchProgramStarterJob() {
        val job: JobDetail = JobBuilder.newJob(ProgramStarterJob::class.java)
                .withIdentity(ProgramStarterJob::class.java.simpleName, ProgramStarterJob.jobGroup)
                .build()

        val trigger = TriggerBuilder.newTrigger()
                .withIdentity("${ProgramStarterJob::class.java.simpleName}Trigger", ProgramStarterJob.jobGroup)
                .withSchedule(CronScheduleBuilder.cronSchedule(programStartCronSchedule))
                .forJob(ProgramStarterJob::class.java.simpleName, ProgramStarterJob.jobGroup)
                .build()

        scheduler?.scheduleJob(job, trigger)
    }
}