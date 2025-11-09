package com.deliverytouroptimizer.deliverytouroptimizerv2.mapper;

import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.vehicle.CreateVehicleRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.vehicle.UpdateVehicleRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.vehicle.VehicleResponse;
import com.deliverytouroptimizer.deliverytouroptimizerv2.model.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VehicleMapper {
    VehicleMapper INSTANCE = Mappers.getMapper(VehicleMapper.class);

    Vehicle toEntity(CreateVehicleRequest dto);
    void updateEntityFromDto(UpdateVehicleRequest dto, @MappingTarget Vehicle vehicle);
    VehicleResponse toResponse(Vehicle vehicle);
}
