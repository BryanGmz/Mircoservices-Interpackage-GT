package com.gt.interpackage.administration.service;

import com.gt.interpackage.administration.model.Fee;
import com.gt.interpackage.administration.repository.FeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class FeeService {

    @Autowired
    private FeeRepository feeRepository;

    public List<Fee> findAll() {
        return feeRepository.findAll();
    }

    public Fee getById(Long id) throws Exception {
        try {
            Fee fee = feeRepository.getById(id);
            if (fee == null) return null;
            if (fee.getName() != null) { }
            return fee;
        } catch (EntityNotFoundException e) {
            return null;
        }
    }

    public <S extends Fee> S save(S entity) {
        return feeRepository.save(entity);
    }

    public <S extends Fee> Fee update(S entity, Long id) throws Exception {
        Fee fee = getById(id);
        if (fee != null) {
            fee.setFee(entity.getFee());
            fee.setName(entity.getName());
            return feeRepository.save(fee);
        }   return null;
    }

    public ResponseEntity<Fee> service(Fee fee, boolean update, Long id) throws URISyntaxException, Exception {
        if (fee != null) {
            if (fee.getFee() == null || fee.getName() == null || fee.getName().isEmpty() || fee.getName().isBlank()) {
                return new ResponseEntity("Todos los campos son obligatorios.", HttpStatus.BAD_REQUEST);
            } else {
                if (fee.getFee() < 0) {
                    return new ResponseEntity("La tarifa debe de ser mayor a 0.", HttpStatus.BAD_REQUEST);
                } else {
                    if (update) {
                        Fee updatedFee = update(fee, id);
                        return updatedFee != null ?
                                ResponseEntity
                                        .ok(updatedFee) :    // 200 OK
                                ResponseEntity              // 404 Not Found
                                        .notFound()
                                        .build();
                    } else {
                        Fee savedFee = save(fee);
                        return ResponseEntity
                                .created (
                                        new URI("/fee/" + savedFee.getId()))
                                .body(savedFee);
                    }
                }
            }
        }
        return ResponseEntity
                .badRequest()
                .build();
    }
}
