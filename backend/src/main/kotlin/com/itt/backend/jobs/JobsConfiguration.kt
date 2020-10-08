/*
 * Copyright (c) 2020. Robert Mayore
 */

package com.itt.backend.jobs

import org.quartz.Scheduler
import org.springframework.beans.factory.annotation.Autowired
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

    @PostConstruct
    fun postConstruct() {
        //For when you need to start the program during startup

        //launchProgramStarterJob(scheduler)
    }
}