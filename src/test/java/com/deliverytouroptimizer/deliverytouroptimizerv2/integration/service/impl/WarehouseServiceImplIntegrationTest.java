package com.deliverytouroptimizer.deliverytouroptimizerv2.integration.service.impl;

import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.warehouse.CreateWareHouseRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.warehouse.WareHouseResponse;
import com.deliverytouroptimizer.deliverytouroptimizerv2.repository.WarehouseRepository;
import com.deliverytouroptimizer.deliverytouroptimizerv2.service.WarehouseService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class WarehouseServiceImplIntegrationTest {
    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Test
    @DisplayName("Should create warehouse successfully using real repository and mapper")
    public void shouldCreateWarehouseIntegrationTest(){
        CreateWareHouseRequest request = new CreateWareHouseRequest(
                "Central Warehouse",
                "123 Main Street, Casablanca",
                33.5731,
                -7.5898,
                LocalTime.parse("08:00:00"),
                LocalTime.parse("18:00:00")
        );

        WareHouseResponse response = warehouseService.create(request);

        assertEquals(request.name(), response.name());
        assertEquals(request.address(), response.address());
        assertEquals(request.latitude(), response.latitude());
        assertEquals(request.longitude(), response.longitude());
        assertEquals(request.openingTime(), response.openingTime());
        assertEquals(request.closingTime(), response.closingTime());

        //check if in db
        assertEquals(1, warehouseRepository.count());
    }
}
