package com.ak.employeeapp.controller

import com.ak.employeeapp.model.Employee
import com.ak.employeeapp.repository.EmployeeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class EmployeeController {

    @Autowired
    lateinit var repository:EmployeeRepository

    @PostMapping("/emp")
     fun addEmployee(@RequestBody payload:Employee) : Employee =  repository.save(payload);

    @GetMapping("/emp/{id}")
     fun getEmployee(@PathVariable id:String) : Optional<Employee> = repository.findById(id.toLong())

    @DeleteMapping("/emp/{id}")
    open fun deleteEmployee(@PathVariable id:String): Unit = repository.deleteById(id.toLong())

    @GetMapping("/getAll")
    fun getAllEmployee() : List<Employee> = repository.findAll();

}