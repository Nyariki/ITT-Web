/*
 * Copyright (c) 2020. Robert Mayore
 */

package com.itt.backend

import com.itt.backend.jobs.*
import org.quartz.JobBuilder
import org.quartz.JobDetail
import org.quartz.Scheduler
import org.quartz.TriggerBuilder
import java.awt.Color
import java.text.SimpleDateFormat
import java.util.*


/**
 * Add seconds to Date and returns new Date
 *
 * @param seconds
 * @return Date
 */
fun Date.addSeconds(seconds: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.SECOND, seconds)
    return calendar.time

}

/**
 * Generates a random color hex
 *
 * @return
 */
fun generateNiceColor() : String {
    val colorPos : Int = 0
    val goldenRatioConj : Double = (1.0 + Math.sqrt(5.0)) / 2.0
    var hue : Float = Random().nextFloat()
    hue += (goldenRatioConj * (colorPos / (5 * Math.random()))).toFloat()
    hue %= 1
    val color = Color.getHSBColor(hue, 0.5f, 0.95f)
    return ("#"+Integer.toHexString(color.rgb).substring(2)).toUpperCase()
}

/**
 * Program time format
 */
val programTimeFormat =  SimpleDateFormat("hh:mm:ss")

/**
 * Default time format
 */
val defaultTimeFormat =  SimpleDateFormat("YYYY-MM-dd hh:mm a")

/**
 * Current Event time format
 */
val currentEventTimeFormat =  SimpleDateFormat("YYYY-MM-dd hh:mm:ss.SSS")

/**
 * Launch program starter job now
 */
fun launchProgramStarterJob(scheduler: Scheduler?) {
    //Stop jobs if running
    stopJobs(scheduler,
            mutableListOf(
                    ProgramTimeJob::class.java.simpleName,
                    ProgramStarterJob::class.java.simpleName,
                    StartServerJob::class.java.simpleName,
                    StopServerJob::class.java.simpleName,
                    ReportServersRunningJob::class.java.simpleName))

    //reset job data
    IttBackendApplication.resetJobData()

    //launch starter job
    val job: JobDetail = JobBuilder.newJob(ProgramStarterJob::class.java)
            .withIdentity(ProgramStarterJob::class.java.simpleName, ProgramStarterJob.jobGroup)
            .build()

    val trigger = TriggerBuilder.newTrigger()
            .withIdentity("${ProgramStarterJob::class.java.simpleName}Trigger", ProgramStarterJob.jobGroup)
            .startNow()
            .forJob(ProgramStarterJob::class.java.simpleName, ProgramStarterJob.jobGroup)
            .build()

    scheduler?.scheduleJob(job, trigger)
}


/**
 * Stop jobs
 *
 * @param scheduler
 * @param names
 */
fun stopJobs(scheduler: Scheduler?, names: MutableList<String>) {
    /*val currentlyExecuting = scheduler?.currentlyExecutingJobs

    if (currentlyExecuting != null) {
        for (jobExecutionContext in currentlyExecuting) {
            if (names.contains(jobExecutionContext.jobDetail.key.name)) {
                scheduler?.interrupt(jobExecutionContext.jobDetail.key)
            }
        }
    }*/
    scheduler?.clear()
}