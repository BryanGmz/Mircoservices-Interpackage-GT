package com.gt.interpackage.receptionist.repository;

import com.gt.interpackage.receptionist.model.PackageCheckpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageCheckpointRepository extends JpaRepository<PackageCheckpoint, Long> {

    public PackageCheckpoint findByPackagesIdAndCurrentCheckpointTrue(Long id);

    @Query(value="SELECT cast(sum(time_on_checkpoint) AS varchar) AS time FROM package_checkpoint WHERE id_package =?1", nativeQuery = true)
    public String getTimeOnRouteByPackageId(Long id);

}