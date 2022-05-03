package com.gt.interpackage.administration.service;

import com.gt.interpackage.administration.model.Queue;
import com.gt.interpackage.administration.repository.QueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueueService {

    @Autowired
    private QueueRepository queueRepository;

    public List<Queue> findByDestination(Long idDestination) {
        return queueRepository.findAllByPackages_Destination_Id(idDestination);
    }

    public boolean deletePackageOnQueue(Queue queue) {
        try {
            queueRepository.delete(queue);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public <S extends Queue> S save(S entity){
        return queueRepository.save(entity);
    }
}
