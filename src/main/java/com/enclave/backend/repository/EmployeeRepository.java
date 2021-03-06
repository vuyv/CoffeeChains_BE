package com.enclave.backend.repository;

import com.enclave.backend.entity.Employee;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Short> {

    Employee findByUsername(String name);

    Optional<Employee> findById(Short id);

    @Query("SELECT e FROM Employee e WHERE e.branch.id= :branchId")
    List<Employee> findByBranch(@Param("branch") short branchId);

    @Query("SELECT COUNT(e) FROM Employee e WHERE e.branch.id= :branchId")
    int getCountOfBranchEmployee(@Param("branch") short branchId);

    @Query(value = "SELECT branch.name,count(*) FROM Employee JOIN branch ON employee.branch_id = branch.id group by branch.name;\n", nativeQuery = true)
    List<Object[]> getCountOfEmployeeEachBranch();

    Optional<Employee> findByPhone(String phone);

    Optional<Employee> findByEmail(String email);

////
////    List<Employee> getManagers();
//
//    @Query("SELECT e FROM Employee e WHERE e.role.id= :roleId")
//    List<Employee> getEmployeeByRole(@Param("role") short roleId);

}