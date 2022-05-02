package com.gt.interpackage.administration.repository;

import com.gt.interpackage.administration.model.EmployeeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeTypeRepository extends JpaRepository<EmployeeType, Long> {

    /**
     * Metodo que se comunica con la base de datos para obtener un objeto
     * de tipo EmployeeType en base al nombre que se recibe como parametro.
     * @return
     */
    public Optional<EmployeeType> findByName(String name);
}
