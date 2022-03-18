package com.ak.employeeapp.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name="employee")
data class Employee(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @Column(name="username", unique = true, nullable = false)
    val username: String,

    @Column(name="first_name", nullable = false)
    val firstName: String,

    @Column(name="last_name", nullable = false)
    val lastName: String,

    @Column(name = "email", nullable = false)
    val  email: String,

    @Column(name = "salary", nullable = false)
    val salary: Long
)