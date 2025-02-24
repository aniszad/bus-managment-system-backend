package com.azcode.demo.modules.jwtauth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BusSystemApplication

fun main(args: Array<String>) {
	runApplication<BusSystemApplication>(*args)
}
