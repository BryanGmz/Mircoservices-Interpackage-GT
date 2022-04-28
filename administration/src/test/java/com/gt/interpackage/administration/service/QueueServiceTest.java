package com.gt.interpackage.administration.service;

import com.gt.interpackage.administration.model.Package;
import com.gt.interpackage.administration.model.Queue;
import com.gt.interpackage.administration.repository.QueueRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;

public class QueueServiceTest {

    @Mock
    private QueueRepository queueRepository;

    @InjectMocks
    private QueueService queueService;

    private Queue queue;
    private Package packet;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        packet = new Package();
        queue = new Queue(15L, packet, 1);
    }

    @Test
    public void testFindByDestination() {
        Mockito.when(
                queueRepository.findAllByPackages_Destination_Id(ArgumentMatchers.any(Long.class)))
                .thenReturn(Arrays.asList(queue));
        List<Queue> packageOnQueue = queueService.findByDestination(1L);
        Assertions.assertNotNull(packageOnQueue);
        Assertions.assertEquals(packageOnQueue.size(), 1);
    }

    @Test
    public void testDeletePackage() {
        Assertions.assertTrue(queueService.deletePackageOnQueue(queue));
    }

    @Test
    public void testSavePackageOnQueue() {
        Mockito.when(
                queueRepository.save(ArgumentMatchers.any(Queue.class)))
                .thenReturn(queue);
        Queue saved = queueService.save(queue);
        Assertions.assertNotNull(saved);
        Assertions.assertEquals(saved.getPosition(), 1);
    }

}
