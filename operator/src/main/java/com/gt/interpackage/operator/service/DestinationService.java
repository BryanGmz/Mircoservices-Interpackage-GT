package com.gt.interpackage.operator.service;

import java.util.List;
import java.util.Optional;

import com.gt.interpackage.operator.model.Destination;
import com.gt.interpackage.operator.repository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Service
public class DestinationService {

    @Autowired
    private DestinationRepository destinationRepository;

    public List<Destination> findAll(){
        return destinationRepository.findAll();
    }
}
