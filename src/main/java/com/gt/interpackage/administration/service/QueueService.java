package com.gt.interpackage.administration.service;

import com.gt.interpackage.administration.model.Queue;
import com.gt.interpackage.administration.repository.QueueRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class QueueService {

    @Autowired
    private QueueRepository queueRepository;

    public List<Queue> findByDestination(Long idDestination) {
        return queueRepository.findAllByPackages_Destination_Id(idDestination);
    }

    public void deletePackageOnQueue(Queue queue) {
        queueRepository.delete(queue);
    }

    public <S extends Queue> S save(S entity){
        return queueRepository.save(entity);
    }
}
