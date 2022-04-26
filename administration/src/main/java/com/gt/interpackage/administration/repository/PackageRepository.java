package com.gt.interpackage.administration.repository;

import com.gt.interpackage.administration.model.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageRepository extends JpaRepository<Package, Long> {

}