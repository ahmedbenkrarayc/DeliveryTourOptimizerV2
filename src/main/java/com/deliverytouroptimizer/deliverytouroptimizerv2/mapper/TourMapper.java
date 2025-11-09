package com.deliverytouroptimizer.deliverytouroptimizerv2.mapper;

import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.tour.CreateTourRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.tour.UpdateTourRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.tour.TourResponse;
import com.deliverytouroptimizer.deliverytouroptimizerv2.model.Tour;
import com.deliverytouroptimizer.deliverytouroptimizerv2.model.Vehicle;
import com.deliverytouroptimizer.deliverytouroptimizerv2.model.Warehouse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {VehicleMapper.class, WarehouseMapper.class})
public interface TourMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "vehicle", expression = "java(toVehicle(request.vehicleId()))")
    @Mapping(target = "warehouse", expression = "java(toWarehouse(request.warehouseId()))")
    @Mapping(target = "deliveries", ignore = true)
    Tour toEntity(CreateTourRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "vehicle", expression = "java(toVehicle(request.vehicleId()))")
    @Mapping(target = "warehouse", expression = "java(toWarehouse(request.warehouseId()))")
    @Mapping(target = "deliveries", ignore = true)
    void updateEntityFromDto(UpdateTourRequest request, @MappingTarget Tour tour);

    TourResponse toResponse(Tour tour);

    default Vehicle toVehicle(Long id) {
        if (id == null) return null;
        Vehicle vehicle = new Vehicle();
        vehicle.setId(id);
        return vehicle;
    }

    default Warehouse toWarehouse(Long id) {
        if (id == null) return null;
        Warehouse warehouse = new Warehouse();
        warehouse.setId(id);
        return warehouse;
    }
}
