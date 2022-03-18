package com.ak.employeeapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories("com.ak.employeeapp.repository")
@EntityScan("com.ak.employeeapp.model")
class EmployeeappApplication

fun main(args: Array<String>) {
    runApplication<EmployeeappApplication>(*args)
}
