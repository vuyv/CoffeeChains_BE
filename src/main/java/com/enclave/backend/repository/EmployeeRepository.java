package com.enclave.backend.repository;

import com.enclave.backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Short> {

    Employee findByUsername(String name);

    Optional<Employee> findById(Short id);
}
