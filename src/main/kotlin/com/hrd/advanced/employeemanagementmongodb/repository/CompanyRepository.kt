package com.hrd.advanced.employeemanagementmongodb.repository

import com.hrd.advanced.employeemanagementmongodb.model.Company
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface CompanyRepository:ReactiveMongoRepository<Company,String> {
}