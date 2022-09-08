package com.hrd.advanced.employeemanagementmongodb.service

import com.hrd.advanced.employeemanagementmongodb.exception.NotFoundException
import com.hrd.advanced.employeemanagementmongodb.model.Company
import com.hrd.advanced.employeemanagementmongodb.model.Employee
import com.hrd.advanced.employeemanagementmongodb.repository.CompanyRepository
import com.hrd.advanced.employeemanagementmongodb.repository.EmployeeRepositpory
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Service
class CompanyService  (
    private val companyRepository : CompanyRepository,
    private val employeeRepositpory: EmployeeRepositpory
){

    fun createCompany(request:CompanyRequest): Mono<Company> =
        companyRepository.save(Company(name = request.name, address = request.address))


    fun findAll():Flux<Company> =
        companyRepository.findAll()

    fun findById(id:String):Mono<Company> =
        companyRepository.findById(id).switchIfEmpty { Mono.error(NotFoundException("Company with id $id not found")) }

    fun updateCompany(id:String, request: CompanyRequest):Mono<Company> =
        findById(id)
            .flatMap {
                companyRepository.save(
                    it.apply {
                        name = request.name
                        address = request.address
                    }
                )
            }
            .doOnSuccess { updateCompanyEmployees(it).subscribe() }


    fun deletebyId(id:String):Mono<Void> =
        findById(id).flatMap  (companyRepository::delete)


    private fun updateCompanyEmployees(updatedCompany: Company): Flux<Employee> =
        employeeRepositpory.saveAll(
            employeeRepositpory.findByCompanyId(updatedCompany.id!!)
                .map { it.apply { company = updatedCompany } }
        )
}


