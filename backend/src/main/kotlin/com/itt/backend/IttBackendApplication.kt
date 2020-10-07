package com.itt.backend

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * Itt backend application
 *
 * @constructor Create empty Itt backend application
 */
@SpringBootApplication
@MapperScan("com.itt.data")
class IttBackendApplication {

}

/**
 * Entry point for whole application
 *
 * @param args
 */
fun main(args: Array<String>) {
    runApplication<IttBackendApplication>(*args)
}



