package com.natandanielapps.consensysbooking.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.natandanielapps.consensysbooking.services.entities.Employee;

/**
 * Repository for {@link Employee} entities.
 * @author Natan
 *
 */
@RepositoryRestResource(exported = false)
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
	
	Optional<Employee> findByUsername(String username);
}
