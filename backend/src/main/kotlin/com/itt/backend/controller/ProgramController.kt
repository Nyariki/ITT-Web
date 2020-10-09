package com.itt.backend.controller

import com.itt.backend.*
import com.itt.backend.jobs.*
import com.itt.backend.service.EventsService
import com.itt.data.model.Event
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

        val body: HashMap<String, Any?> = HashMap()

        body["status"] = true
        body["message"] = "Program started successfully at ${defaultTimeFormat.format(IttBackendApplication.programStart.get())}"
        body["data"] = defaultTimeFormat.format(IttBackendApplication.programTimeStart.get())

        return ResponseEntity.status(HttpStatus.OK).body(body)
    }

    /**
     * Will fetch all the events
     *
     * @return
     */
    @GetMapping("report")
    fun allEvents(): ResponseEntity<Any> {
        val body: HashMap<String, Any?> = HashMap()
        body["status"] = true
        body["message"] = "report fetched successfully"

        val list: MutableList<Event> = eventsService?.getAllEvents() ?: mutableListOf()

        //conflate events happening at same time
        list.groupBy { it.time }.mapValues {
            it.value.apply {
                val list = it.value.toMutableList()
                val event1 = list[0]
                list.removeAt(0)
                list.forEach { event -> event1.message = event1.message + ", ${event.message}" }
                listOf(event1)
            }
        }

        //remove created at, updated at
        list.map {
            it.createdAt = null
            it.updatedAt = null
            it
        }

        body["list"] = list

        return ResponseEntity.status(HttpStatus.OK).body(body)
    }


    /**
     * Start
     *
     * @param time in format HH:mm:ss
     * @param color in format #AABBCC
     * @return
     */
    @PostMapping("get-current-tasks")
    fun eventByTime(@RequestParam(value = "time", required = true) time: String, @RequestParam(value = "color", required = true) color: String): ResponseEntity<Any> {
        //TODO: Reset color
        val body: HashMap<String, Any?> = HashMap()
        body["status"] = true
        body["message"] = "events fetched successfully"
        body["events"] = eventsService?.getEventByTimeAndColor(time, color)

        return ResponseEntity.status(HttpStatus.OK).body(body)
    }

}