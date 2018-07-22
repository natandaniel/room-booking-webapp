package com.natandanielapps.consensysbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.natandanielapps.consensysbooking.model.Employee;

@RepositoryRestResource(exported = false)
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	Employee findByLastName(String lastName);
}
