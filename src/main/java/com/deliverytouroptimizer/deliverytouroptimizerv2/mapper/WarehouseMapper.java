package com.deliverytouroptimizer.deliverytouroptimizerv2.mapper;

import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.warehouse.CreateWareHouseRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.warehouse.UpdateWareHouseRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.warehouse.WareHouseResponse;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.warehouse.WareHouseTourResponse;
import com.deliverytouroptimizer.deliverytouroptimizerv2.model.Tour;
import com.deliverytouroptimizer.deliverytouroptimizerv2.model.Warehouse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WarehouseMapper {

    WarehouseMapper INSTANCE = Mappers.getMapper(WarehouseMapper.class);

    Warehouse toEntity(CreateWareHouseRequest dto);

    void updateEntityFromDto(UpdateWareHouseRequest dto, @MappingTarget Warehouse entity);

    WareHouseResponse toResponse(Warehouse entity);

    default List<WareHouseTourResponse> mapTours(List<Tour> tours) {
        if (tours == null) return null;
        return tours.stream()
                .map(t -> new WareHouseTourResponse(
                        t.getId(),
                        t.getDate(),
                        t.getVehicle() != null ? t.getVehicle().getId() : null
                ))
                .toList();
    }
}