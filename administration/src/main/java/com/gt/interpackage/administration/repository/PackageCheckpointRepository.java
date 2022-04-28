package com.gt.interpackage.administration.repository;

import com.gt.interpackage.administration.model.PackageCheckpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PackageCheckpointRepository extends JpaRepository<PackageCheckpoint, Long> {

    public boolean existsPackageCheckpointByCheckpointId(Long id);

    @Transactional
    @Modifying
    @Query(value="INSERT INTO package_checkpoint (current_checkpoint, id_package, id_checkpoint) VALUES(?1, ?2, ?3) ", nativeQuery = true)
    public void create(Boolean currentCheckpoint, Long packageId, Long checkpointId);

}
