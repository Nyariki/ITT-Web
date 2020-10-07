/*
 * Copyright (c) 2020. Robert Mayore
 */

package com.itt.backend

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