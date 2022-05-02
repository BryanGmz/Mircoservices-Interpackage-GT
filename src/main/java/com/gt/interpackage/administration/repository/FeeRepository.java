package com.gt.interpackage.administration.repository;

import com.gt.interpackage.administration.model.Fee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeeRepository extends JpaRepository<Fee, Long> {

}
