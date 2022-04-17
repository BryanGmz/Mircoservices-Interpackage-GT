package com.gt.interpackage.reports.service;

import com.gt.interpackage.reports.dto.TopRouteDTO;
import com.gt.interpackage.reports.model.Route;
import com.gt.interpackage.reports.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class RouteService {

    @Autowired
    private RouteRepository routeRepository;

    public static final String QUERY_TOP_FILTER =
            "SELECT COUNT(route.name) AS quantity, route.name AS route, destination.name AS destination "
                    + "FROM route "
                    + "INNER JOIN destination ON route.id_destination = destination.id "
                    + "INNER JOIN package ON package.route = route.id "
                    + "WHERE package.end_date BETWEEN :startDate AND :endDate "
                    + "GROUP BY route.name, destination.name "
                    + "ORDER BY quantity DESC "
                    + "LIMIT 3 ";

    public static final String QUERY_TOP =
            "SELECT COUNT(route.name) AS quantity, route.name AS route, destination.name AS destination "
                    + "FROM route "
                    + "INNER JOIN destination ON route.id_destination = destination.id "
                    + "INNER JOIN package ON package.route = route.id "
                    + "GROUP BY route.name, destination.name "
                    + "ORDER BY quantity DESC "
                    + "LIMIT 3 ";

    /**
     * Metodo que llama al repositorio de rutas para obtener todas
     * las rutas y retornarlas en una paginacion.
     * @param pageable
     * @return
     */
    public Page<Route> getAll(Pageable pageable){
        return routeRepository.findAll(pageable);
    }

    public Page<Route> getRoutesByActive(Pageable pageable, Boolean active) {
        return routeRepository.findAllByActive(pageable, active);
    }

    public List<TopRouteDTO> getTopRoute(EntityManager entityManager, String start, String end) throws ParseException {
        List<TopRouteDTO> listTopRoutes;
        SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
        Query query = entityManager.createNativeQuery(QUERY_TOP_FILTER);
        if (end == null && start == null) {
            query = entityManager.createNativeQuery(QUERY_TOP);
            return query.getResultList();
        }
        if (end == null) {
            Date startDate = dtf.parse(start);
            Calendar calendar = Calendar.getInstance();
            Date dateObj = calendar.getTime();
            query.setParameter("startDate", startDate, TemporalType.DATE);
            query.setParameter("endDate", dtf.format(dateObj));
        } else {
            Date startDate = dtf.parse(start);
            Date endDate = dtf.parse(end);
            query.setParameter("startDate", startDate, TemporalType.DATE);
            query.setParameter("endDate", endDate, TemporalType.DATE);
        }
        listTopRoutes = query.getResultList();
        return listTopRoutes;
    }
}
