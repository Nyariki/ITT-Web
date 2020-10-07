package com.itt.backend.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class RequestController {

    @GetMapping("/health")
    fun health(): ResponseEntity<Any> = ResponseEntity.status(HttpStatus.OK).body("Health OK!!")

    @GetMapping("/ok")
    fun ok(): String = "YES"

}