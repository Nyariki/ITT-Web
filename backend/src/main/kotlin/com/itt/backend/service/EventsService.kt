/*
 * Copyright (c) 2020. Robert Mayore
 */

package com.itt.backend.service

import com.itt.data.model.Event

interface EventsService {
    fun deleteAllEvents()
    fun addEvent(event : Event)
    fun getEventByTimeAndColor(time : String, color : String) : MutableList<Event>?
    fun getAllEvents() : MutableList<Event>?
}