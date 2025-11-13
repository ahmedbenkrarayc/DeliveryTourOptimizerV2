package com.deliverytouroptimizer.deliverytouroptimizerv2.service.impl;

import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.warehouse.CreateWareHouseRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.warehouse.WareHouseResponse;
import com.deliverytouroptimizer.deliverytouroptimizerv2.mapper.WarehouseMapper;
import com.deliverytouroptimizer.deliverytouroptimizerv2.model.Warehouse;
import com.deliverytouroptimizer.deliverytouroptimizerv2.repository.WarehouseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WarehouseServiceImplTest {
    @Mock
    private WarehouseRepository warehouseRepository;
    @Mock
    private WarehouseMapper warehouseMapper;
    @InjectMocks
    private WarehouseServiceImpl warehouseServiceImpl;

    @Test
    @DisplayName("Should create warehouse successfully when valid request exists")
    public void shouldCreateWarehouseSuccessfully() {
        //Given
        CreateWareHouseRequest request = new CreateWareHouseRequest(
                "Central Warehouse",
                "123 Main Street, Casablanca",
                33.5731,
                -7.5898,
                LocalTime.parse("08:00:00"),
                LocalTime.parse("18:00:00")
        );

        Warehouse warehouseEntity = Warehouse.builder()
                .name(request.name())
                .address(request.address())
                .latitude(request.latitude())
                .longitude(request.longitude())
                .openingTime(request.openingTime())
                .closingTime(request.closingTime())
                .build();

        Warehouse savedWarehouse = Warehouse.builder()
                .id(1L)
                .name(request.name())
                .address(request.address())
                .latitude(request.latitude())
                .longitude(request.longitude())
                .openingTime(request.openingTime())
                .closingTime(request.closingTime())
                .build();

        WareHouseResponse expectedResponse = new WareHouseResponse(
                1L,
                request.name(),
                request.address(),
                request.latitude(),
                request.longitude(),
                request.openingTime(),
                request.closingTime(),
                null
        );

        //When
        when(warehouseMapper.toEntity(request)).thenReturn(warehouseEntity);
        when(warehouseRepository.save(warehouseEntity)).thenReturn(savedWarehouse);
        when(warehouseMapper.toResponse(savedWarehouse)).thenReturn(expectedResponse);

        WareHouseResponse actualResponse = warehouseServiceImpl.create(request);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse.id(), actualResponse.id());
        assertEquals(expectedResponse.name(), actualResponse.name());
        assertEquals(expectedResponse.address(), actualResponse.address());
        assertEquals(expectedResponse.latitude(), actualResponse.latitude());
        assertEquals(expectedResponse.longitude(), actualResponse.longitude());
        assertEquals(expectedResponse.openingTime(), actualResponse.openingTime());
        assertEquals(expectedResponse.closingTime(), actualResponse.closingTime());
    }
}