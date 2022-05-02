package com.gt.interpackage.administration.repository;

import com.gt.interpackage.administration.model.Employee;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    @Query(value = "SELECT * FROM employee e WHERE e.activo = true", nativeQuery = true)
    List<Employee> getAllActivates();
    
    @Query(value = "SELECT * FROM employee e WHERE e.activo = true AND e.type <> 1", nativeQuery = true)
    List<Employee> getAllActivatesNotAdmin();
    
    @Query(value = "SELECT * FROM employee e WHERE e.activo = false", nativeQuery = true)
    List<Employee> getAllDeactivates();
    
    
    public boolean existsEmployeeByUsername(String username);
    
    public boolean existsEmployeeByCUI(Long CUI);
    
    @Query(value = "SELECT * FROM Employee WHERE CAST(cui AS TEXT) LIKE ?1% AND type = ?2 AND activo=true", nativeQuery = true)
    public Optional<List<Employee>> findByCuiContainsAndEmployeeTypeIs(String cui, Integer employeeType);
}
