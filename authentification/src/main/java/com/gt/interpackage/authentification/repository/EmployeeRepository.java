package com.gt.interpackage.authentification.repository;

import com.gt.interpackage.authentification.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    public Employee getByUsernameOrEmail(String username, String email);

    public Employee findByTokenPassword(String tokenPassword);

}