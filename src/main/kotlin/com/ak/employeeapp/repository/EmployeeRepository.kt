package com.ak.employeeapp.repository

import com.ak.employeeapp.model.Employee

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
public interface EmployeeRepository : JpaRepository<Employee, Long>