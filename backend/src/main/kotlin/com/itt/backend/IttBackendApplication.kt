package com.itt.backend

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.concurrent.atomic.AtomicInteger

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



