package com.itt.backend.controller

import com.itt.backend.defaultTimeFormat
import com.itt.backend.jobs.*
import com.itt.backend.launchProgramStarterJob
import com.itt.backend.programTimeFormat
import com.itt.backend.service.EventsService
import com.itt.backend.stopJobs
import org.quartz.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.collections.HashMap


@RestController
class ProgramController {

    @Autowired
    private val scheduler: Scheduler? = null

    @Autowired
    private val eventsService: EventsService? = null

    /**
     * Health. Check if the API is responding
     *
     * @return
     */
    @GetMapping("health")
    fun health(): ResponseEntity<Any> = ResponseEntity.status(HttpStatus.OK).body("Health OK!!")

    /**
     * Will start the task tracker program
     *
     * @return
     */
    @GetMapping("start")
    fun start(): ResponseEntity<Any> {
        launchProgramStarterJob(scheduler)
        return ResponseEntity.status(HttpStatus.OK).body("Program started at ${defaultTimeFormat.format(Date())}")
    }

    /**
     * Start
     *
     * @param time in format HH:mm:ss
     * @param color in format #AABBCC
     * @return
     */
    @PostMapping("get-current-tasks")
    fun start(@RequestParam(value = "time", required = true) time: String, @RequestParam(value = "color", required = true) color: String): ResponseEntity<Any> {
        //TODO: Reset color
        val body: HashMap<String, Any?> = HashMap()
        body["success"] = true
        body["message"] = "events fetched successfully"
        body["events"] = eventsService?.getEventByTimeAndColor(time, color)

        return ResponseEntity.status(HttpStatus.OK).body(body)
    }

}