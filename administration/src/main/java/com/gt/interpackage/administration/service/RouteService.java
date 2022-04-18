package com.gt.interpackage.administration.service;

import com.gt.interpackage.administration.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RouteService {

    @Autowired
    private RouteRepository routeRepository;

    /**
     * Metodo que llama al repositorio de rutas para consultar si existe una
     * ruta cuyo id sea el  parametro que se recibe.
     * @param id
     * @return True o False.
     */
    public boolean existsById(Long id){
        return routeRepository.existsById(id);
    }
}
