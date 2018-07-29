package com.natandanielapps.consensysbooking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.natandanielapps.consensysbooking.model.Employee;

@RepositoryRestResource(exported = false)
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	Optional<Employee> findByName(String name);
}
