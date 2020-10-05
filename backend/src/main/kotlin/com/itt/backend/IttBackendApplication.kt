package com.itt.backend

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@MapperScan("com.itt.data.dao")
class IttBackendApplication

fun main(args: Array<String>) {
    runApplication<IttBackendApplication>(*args)
}

