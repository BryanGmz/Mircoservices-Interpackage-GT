package com.gt.interpackage.operator.service;

import com.gt.interpackage.administration.source.BadRequestException;
import com.gt.interpackage.operator.handlers.QueueHandler;
import com.gt.interpackage.operator.model.Checkpoint;
import com.gt.interpackage.operator.model.Package;
import com.gt.interpackage.operator.model.PackageCheckpoint;
import com.gt.interpackage.operator.repository.PackageCheckpointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Time;
import java.util.List;

@Service
public class PackageCheckpointService {

    @Autowired
    private PackageCheckpointRepository packageCheckpointRepository;

    @Autowired
    private CheckpointService checkpointService;

    @Autowired
    private PackageService packageService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private QueueHandler queueHandler;

    public List<PackageCheckpoint> getAllPackageCheckpointOnCheckpoint(Long id){
        return packageCheckpointRepository.findAllByCheckpointIdAndCurrentCheckpointTrue(id);
    }

    public PackageCheckpoint getPackageCheckpoint(Long id){
        return packageCheckpointRepository.findByPackagesIdAndCurrentCheckpointTrue(id);
    }

    public String getTimeOnRouteByPackageId(Long id){
        return packageCheckpointRepository.getTimeOnRouteByPackageId(id);
    }

    public PackageCheckpoint getPackageCheckpointWithTimeOnRoute(Long id){
        PackageCheckpoint tempPackageCheckpoint = this.getPackageCheckpoint(id);

        String timeOnRoute = this.getTimeOnRouteByPackageId(id);
        if(timeOnRoute != null)
            tempPackageCheckpoint.setTimeOnCheckpoint(Time.valueOf(timeOnRoute) );
        else
            tempPackageCheckpoint.setTimeOnCheckpoint(new Time(00,00,00));

        return tempPackageCheckpoint;
    }

    private PackageCheckpoint getByCheckpointIdPackageId(Long checkpointId, Long packageId){
        return packageCheckpointRepository.findByCheckpointIdAndPackagesId(checkpointId, packageId);
    }

    private Long getNextCheckpointId(Long packageId, Long routeId){
        return packageCheckpointRepository.getNextCheckpointId(packageId, routeId);
    }

    private  void update(PackageCheckpoint packageCheckpoint){
        packageCheckpointRepository.update(
                packageCheckpoint.getCurrentCheckpoint(),
                packageCheckpoint.getTimeOnCheckpoint(),
                packageCheckpoint.getDate(),
                packageCheckpoint.getPackages().getId(),
                packageCheckpoint.getCheckpoint().getId());
    }

    public void create(PackageCheckpoint packageCheckpoint){
        packageCheckpointRepository.create(
                packageCheckpoint.getCurrentCheckpoint(),
                packageCheckpoint.getPackages().getId(),
                packageCheckpoint.getCheckpoint().getId());
    }

    public void process(PackageCheckpoint  packageCheckpoint) throws Exception {
            if(packageCheckpoint.getTimeOnCheckpoint() == null)
                throw  new BadRequestException("Tiempo es un campo obligatorio");

            if(packageCheckpoint.getTimeOnCheckpoint().toString().isBlank() || packageCheckpoint.getTimeOnCheckpoint().toString().isEmpty())
                throw  new BadRequestException("Tiempo no valido");

            //Obtener instancia con todos los datos almacenados en la base de datos.
            PackageCheckpoint tempPackageCheckpoint = this.getByCheckpointIdPackageId(
                    packageCheckpoint.getCheckpoint().getId(),
                    packageCheckpoint.getPackages().getId());

            //Obtener el id del siguiente punto de control.
            Long nextCheckpointId = this.getNextCheckpointId(
                    tempPackageCheckpoint.getPackages().getId(),
                    tempPackageCheckpoint.getCheckpoint().getRoute().getId());

            //Enviar paquete a siguiente punto de control
            if(nextCheckpointId != null){

                //Obtener punto de control siguiente
                Checkpoint nextChekpoint = checkpointService.getCheckpointById(nextCheckpointId);

                //Si hay espacio en la cola del punto de control siguiente
                if(nextChekpoint.getPackagesOnQueue() < nextChekpoint.getQueueCapacity()){

                    //Actualizar la tupla actual en package_checkpoint
                    this.update(packageCheckpoint);

                    //Insertar la nueva tupla en package_checkpoint
                    this.create(new PackageCheckpoint(nextChekpoint, packageCheckpoint.getPackages(), null, true));

                    //Disminuir en uno packages_on_queue en punto de control actual
                    tempPackageCheckpoint.getCheckpoint().setPackagesOnQueue(tempPackageCheckpoint.getCheckpoint().getPackagesOnQueue()-1);
                    checkpointService.create(tempPackageCheckpoint.getCheckpoint());

                    //Aumentar en uno packages_on_queue en punto de control siguiente
                    nextChekpoint.setPackagesOnQueue(nextChekpoint.getPackagesOnQueue()+1);
                    checkpointService.create(nextChekpoint);
                }

                //Si la cola del punto de control siguiente esta llena
                else
                    throw  new BadRequestException("No se puede procesar el paquete. La cola del siguiente punto de control se encuentra llena.");
            }

            //Enviar paquete a destino
            else{
                //Obtener el paquete y actualizarlo
                Package tempPackage = packageService.getById(packageCheckpoint.getPackages().getId());
                tempPackage.setAtDestination(true);
                tempPackage.setOnWay(false);
                tempPackage.setDateEnd(packageCheckpoint.getDate().toLocalDate());
                packageService.update(tempPackage, tempPackage.getId());

                //Actualizar la tupla actual en package_checkpoint
                this.update(packageCheckpoint);

                //Disminuir en uno packages_on_queue en punto de control actual
                tempPackageCheckpoint.getCheckpoint().setPackagesOnQueue(tempPackageCheckpoint.getCheckpoint().getPackagesOnQueue()-1);
                checkpointService.create(tempPackageCheckpoint.getCheckpoint());

                //Disminuir en uno packages_on_route en la ruta actual
                tempPackageCheckpoint.getCheckpoint().getRoute().setPackagesOnRoute(tempPackageCheckpoint.getCheckpoint().getRoute().getPackagesOnRoute()-1);
                routeService.update(tempPackageCheckpoint.getCheckpoint().getRoute());
            }

            queueHandler.verifiyQueue(this);
    }

}
