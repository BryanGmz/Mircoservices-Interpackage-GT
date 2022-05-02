package com.gt.interpackage.administration.repository;

import com.gt.interpackage.administration.model.PackageCheckpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageCheckpointRepository extends JpaRepository<PackageCheckpoint, Long> {

    public boolean existsPackageCheckpointByCheckpointId(Long id);

}
