/*
 * Copyright (c) 2020. Robert Mayore
 */

package com.itt.backend.service

import com.itt.data.dao.EventsDao
import com.itt.data.model.Event
import com.itt.data.model.EventExample
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EventsServiceImpl : EventsService {

    @Autowired
    private val eventsDao: EventsDao? = null

    override fun getEventByTime(time: String): MutableList<Event>? {
        val eventExample = EventExample()
        eventExample.createCriteria().andTimeLike(time)
        return eventsDao?.selectByExample(eventExample)
    }

    override fun getAllEvents(): MutableList<Event>? {
        val eventExample = EventExample()
        return eventsDao?.selectByExample(eventExample)
    }

    override fun deleteAllEvents() {
        val eventExample = EventExample()
        eventExample.createCriteria().andIdIsNotNull() //will include all DB records
        eventsDao?.deleteByExample(eventExample)
    }

    override fun addEvent(event: Event) {
        eventsDao?.insert(event)
    }
}