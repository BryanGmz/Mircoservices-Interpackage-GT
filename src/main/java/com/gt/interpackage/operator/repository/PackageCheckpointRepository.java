package com.gt.interpackage.operator.repository;

import com.gt.interpackage.operator.model.PackageCheckpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Time;
import java.util.Date;
import java.util.List;

@Repository
public interface PackageCheckpointRepository extends JpaRepository<PackageCheckpoint, Long> {

    public List<PackageCheckpoint> findAllByCheckpointIdAndCurrentCheckpointTrue(Long id);

    public PackageCheckpoint findByPackagesIdAndCurrentCheckpointTrue(Long id);

    public PackageCheckpoint findByCheckpointIdAndPackagesId(Long checkpointId, Long packageId);

    @Query(value="SELECT cast(sum(time_on_checkpoint) AS varchar) AS time FROM package_checkpoint WHERE id_package =?1", nativeQuery = true)
    public String getTimeOnRouteByPackageId(Long id);

    @Query(value="SELECT c.id FROM checkpoint c WHERE c.id NOT IN("
            + "SELECT id_checkpoint FROM package_checkpoint WHERE id_package = ?1)"
            + "AND id_route  = ?2 ORDER BY c.id ASC LIMIT 1",
            nativeQuery = true)
    public Long getNextCheckpointId(Long packageId, Long idRoute);

    @Transactional
    @Modifying
    @Query(value="UPDATE package_checkpoint SET current_checkpoint=?1, time_on_checkpoint=?2, date=?3 WHERE id_package=?4 AND id_checkpoint=?5", nativeQuery = true)
    public void update(Boolean currentCheckpoint, Time timeOnCheckpoint, Date date, Long idPackage, Long idCheckpoint);

    @Transactional
    @Modifying
    @Query(value="INSERT INTO package_checkpoint (current_checkpoint, id_package, id_checkpoint) VALUES(?1, ?2, ?3) ", nativeQuery = true)
    public void create(Boolean currentCheckpoint, Long packageId, Long checkpointId);

}
