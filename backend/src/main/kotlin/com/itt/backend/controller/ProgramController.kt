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
        body["object"] = defaultTimeFormat.format(IttBackendApplication.programTimeStart.get())

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

        //conflate events happening at the same time
        var newList = list.toMutableList()
                .groupBy { it.time }
                .apply {
                    this.values.forEach {
                        val tempList = it.toCollection(mutableListOf())
                        tempList.removeAt(0)
                        tempList.forEach { event -> it[0].message = it[0].message + ", ${event.message}" }
                    }
                }.values.map {
            it.toMutableList().distinctBy { it.time }.map {
                it?.createdAt = null
                it?.updatedAt = null
                it
            }
        }.toMutableList().flatten()

        body["list"] = newList

        return ResponseEntity.status(HttpStatus.OK).body(body)
    }


    /**
     * Retrieves current events
     *
     * @param time
     * @param startColor
     * @param stopColor
     * @param reportColor
     * @return
     */
    @PostMapping("get-current-events")
    fun eventByTime(@RequestParam(value = "time", required = true) time: String,
                    @RequestParam(value = "start_color", required = false) startColor: String?,
                    @RequestParam(value = "stop_color", required = false) stopColor: String?,
                    @RequestParam(value = "report_color", required = false) reportColor: String?): ResponseEntity<Any> {

        val body: HashMap<String, Any?> = HashMap()
        val data: HashMap<String, Any?> = HashMap()

        //check if program is started
        if(IttBackendApplication.programStart.get() == null){
            body["status"] = false
            body["message"] = "program not started yet"

            return ResponseEntity.status(HttpStatus.OK).body(body)
        }

        //get program time from current time in request
        val date = currentEventTimeFormat.parse(time)
        val offset = IttBackendApplication.programTimeStart.get().time - IttBackendApplication.programStart.get().time
        val programTime = programTimeFormat.format(Date(date.time + offset))

        //get events
        val events = eventsService?.getEventByTime(programTime)
        events?.forEach {
            when (it.type) {
                1L -> data["start_color"] = IttBackendApplication.startJobColor.updateAndGet { generateNiceColor() }
                2L -> data["stop_color"] = IttBackendApplication.stopJobColor.updateAndGet { generateNiceColor() }
                3L -> data["report_color"] = IttBackendApplication.reportJobColor.updateAndGet { generateNiceColor() }
            }
            //remove created at, updated at
            it.createdAt = null
            it.updatedAt = null
        }

        //prepare response data
        data["time"] = currentEventTimeFormat.format(IttBackendApplication.programTime.get().time)
        data["events"] = events

        body["status"] = true
        body["message"] = "events fetched successfully"
        body["object"] = data

        return ResponseEntity.status(HttpStatus.OK).body(body)
    }

}