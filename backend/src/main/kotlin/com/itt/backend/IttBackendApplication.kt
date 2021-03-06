package com.itt.backend

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference




/**
 * Itt backend application
 *
 * @constructor Create empty Itt backend application
 */
@SpringBootApplication
@MapperScan("com.itt.data")
class IttBackendApplication {

    companion object{
        //Monitor servers running
        val serversRunning = AtomicInteger()

        //Colors
        val startJobColor = AtomicReference<String>()
        val stopJobColor = AtomicReference<String>()
        val reportJobColor = AtomicReference<String>()

        //time
        val programStart = AtomicReference<Date>() //program start time in real time
        val programTimeStart = AtomicReference<Date>() //program start time in program time
        val programTime = AtomicReference<Calendar>() //current program time

        fun resetJobData(){
            serversRunning.set(0)

            startJobColor.set(generateNiceColor())
            stopJobColor.set(generateNiceColor())
            reportJobColor.set(generateNiceColor())

            val cal = Calendar.getInstance()
            programStart.set(cal.time)
            cal.set(Calendar.HOUR_OF_DAY, 12)
            cal.set(Calendar.MINUTE, 0)
            cal.set(Calendar.SECOND, 0)
            cal.set(Calendar.MILLISECOND, 0)
            programTime.set(cal)
            programTimeStart.set(cal.time)
        }
    }

}

/**
 * Entry point for whole application
 *
 * @param args
 */
fun main(args: Array<String>) {
    runApplication<IttBackendApplication>(*args)
}



