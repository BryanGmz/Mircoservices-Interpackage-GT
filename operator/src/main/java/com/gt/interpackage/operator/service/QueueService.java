package com.gt.interpackage.operator.service;

import com.gt.interpackage.operator.model.Queue;
import com.gt.interpackage.operator.repository.QueueRepository;
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

    public void deletePackageOnQueue(Queue queue) {
        queueRepository.delete(queue);
    }

}
