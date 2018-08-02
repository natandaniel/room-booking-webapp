package com.natandanielapps.consensysbooking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.natandanielapps.consensysbooking.services.entities.Employee;

//@RepositoryRestResource(exported = false)
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	Optional<Employee> findByUsername(String username);
}
