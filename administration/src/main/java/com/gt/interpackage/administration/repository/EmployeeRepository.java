package com.gt.interpackage.administration.repository;

import com.gt.interpackage.administration.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(value = "SELECT * FROM Employee WHERE CAST(cui AS TEXT) LIKE ?1% AND type = ?2 AND activo=true", nativeQuery = true)
    public Optional<List<Employee>> findByCuiContainsAndEmployeeTypeIs(String cui, Integer employeeType);
}
