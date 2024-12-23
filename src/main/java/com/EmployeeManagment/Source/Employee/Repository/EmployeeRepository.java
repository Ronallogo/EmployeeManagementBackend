package com.EmployeeManagment.Source.Employee.Repository;

import com.EmployeeManagment.Source.Employee.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


@EnableJpaRepositories
public interface EmployeeRepository  extends JpaRepository<Employee, Long> {
    @Query(value = "SELECT * FROM employee WHERE name LIKE %:keyword% OR surname LIKE  %:keyword%", nativeQuery = true)
    List<Employee> researchEmployee(@Param("keyword") String keyword);

    Optional<Employee> findByEmail(String email);

    @Query(value = "SELECT * FROM employee WHERE  user_id = :id"  , nativeQuery = true)
    Optional<Employee> findEmployeeByIdUser(@Param("id")Long id);
}
