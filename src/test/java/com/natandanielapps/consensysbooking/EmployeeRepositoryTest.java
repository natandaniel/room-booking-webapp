package com.natandanielapps.consensysbooking;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.natandanielapps.consensysbooking.repository.EmployeeRepository;
import com.natandanielapps.consensysbooking.services.entities.Employee;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private EmployeeRepository employees;

	@Test
	public void whenFindByUsername_thenReturnEmployee() {

		Employee test = new Employee("test", "test", "test", "ROLE_USER");
		entityManager.persist(test);
		entityManager.flush();

		Optional<Employee> found = employees.findByUsername(test.getUsername());

		// then
		assertEquals(found.get().getUsername(), test.getUsername());
	}

}
