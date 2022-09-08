package com.hrd.advanced.employeemanagementmongodb.repository

import com.hrd.advanced.employeemanagementmongodb.model.Employee
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface EmployeeRepositpory:ReactiveMongoRepository<Employee,ObjectId> {
    fun findByCompanyId(id:String):Flux<Employee>
}