package com.gt.interpackage.administration.service;

import com.gt.interpackage.administration.model.Fee;
import com.gt.interpackage.administration.repository.FeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

public class FeeServiceTest {

    @Mock
    private FeeRepository feeRepository;

    @InjectMocks
    private FeeService feeService;

    private Fee fee;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        fee = new Fee(1L, "Tarifa por operacion", 15.50);
    }

    @Test
    public void testGetListWithRates() {
        Mockito.when(
                feeRepository.findAll())
                .thenReturn(
                        Arrays.asList(fee));
        assertNotNull(feeService.findAll());
        assertEquals(feeService.findAll().size(), 1);
    }

    @Test
    public void testGetFeePerId() throws Exception {
        String nameFee = "Tarifa por operacion";
        Mockito.when(
                feeRepository.getById(ArgumentMatchers.any(Long.class)))
                .thenReturn(fee);
        Fee searched = feeService.getById(1L);
        assertNotNull(searched);
        assertEquals(searched.getName(), nameFee);
        assertEquals(searched.getId(), 1L);
        Mockito.verify(feeRepository).getById(ArgumentMatchers.any(Long.class));
    }

    @Test
    public void testNoFeePerIdFound() throws Exception {
        Mockito.when(
                        feeRepository.getById(ArgumentMatchers.any(Long.class)))
                .thenReturn(null);
        Fee searched = feeService.getById(1L);
        assertNull(searched);
        Mockito.verify(feeRepository).getById(ArgumentMatchers.any(Long.class));
    }

    @Test
    public void testSaveFee() {
        Mockito.when(
                feeRepository.save(ArgumentMatchers.any(Fee.class)))
                .thenReturn(fee);
        Fee save = feeService.save(new Fee("Tarifa por operacion", 15.50));
        assertNotNull(save);
        assertEquals(save.getName(), "Tarifa por operacion");
        Mockito.verify(feeRepository).save(ArgumentMatchers.any(Fee.class));
    }

    @Test
    public void testFeeUpdateSuccessful() throws Exception {
        Fee updated = new Fee(1L, "Tarifa por operacion", 85.50);
        Mockito.when(
                        feeRepository.getById(ArgumentMatchers.any(Long.class)))
                .thenReturn(fee);
        Mockito.when(
                        feeRepository.save(ArgumentMatchers.any(Fee.class)))
                .thenReturn(new Fee(1L, "Tarifa por operacion", 85.50));
        Fee feeUpdate = feeService.update(updated, 1L);
        assertNotNull(feeUpdate);
        assertEquals(updated.getFee(), feeUpdate.getFee());
        Mockito.verify(feeRepository).save(ArgumentMatchers.any(Fee.class));
        Mockito.verify(feeRepository).getById(ArgumentMatchers.any(Long.class));
    }

    @Test
    public void testTheFeeWasNotUpdatedBecauseTheIdWasNotFound() throws Exception {
        Fee updated = new Fee(1L, "Tarifa por operacion", 85.50);
        Mockito.when(
                        feeRepository.getById(ArgumentMatchers.any(Long.class)))
                .thenReturn(null);
        Mockito.when(
                        feeRepository.save(ArgumentMatchers.any(Fee.class)))
                .thenReturn(null);
        Fee feeUpdate = feeService.update(updated, 1L);
        assertNull(feeUpdate);
    }

    @Test
    public void testANullFeeObjectWasSent() throws Exception {
        ResponseEntity responseEntity = feeService.service(null, false, null);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testNotAllRequiredFieldsWereSubmitted() throws Exception {
        ResponseEntity responseEntity = feeService.service(new Fee(null, null, null), false, null);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testTheFeeFieldIsLessThanZero() throws Exception {
        fee.setFee(-0.5);
        ResponseEntity responseEntity = feeService.service(fee, false, null);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testFeeCreated() throws Exception {
        Mockito.when(
                feeRepository.save(ArgumentMatchers.any(Fee.class)))
                .thenReturn(fee);
        ResponseEntity responseEntity = feeService.service(fee, false, null);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
        Mockito.verify(feeRepository).save(ArgumentMatchers.any(Fee.class));
    }

    @Test
    public void testFeeUpdated() throws Exception {
        Mockito.when(
                feeRepository.save(ArgumentMatchers.any(Fee.class)))
                .thenReturn(fee);
        Mockito.when(
                feeRepository.getById(ArgumentMatchers.any(Long.class)))
                .thenReturn(fee);
        ResponseEntity responseEntity = feeService.service(fee, true, 1L);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        Mockito.verify(feeRepository).save(ArgumentMatchers.any(Fee.class));
        Mockito.verify(feeRepository).getById(ArgumentMatchers.any(Long.class));
    }

    @Test
    public void testTheRateToUpdateWasNotFound() throws Exception {
        Mockito.when(
                        feeRepository.getById(ArgumentMatchers.any(Long.class)))
                .thenReturn(null);
        ResponseEntity responseEntity = feeService.service(fee, true, 1L);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
        Mockito.verify(feeRepository).getById(ArgumentMatchers.any(Long.class));
    }
}
