package com.deliverytouroptimizer.deliverytouroptimizerv2.mapper;

import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.delivery.CreateDeliveryRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.delivery.UpdateDeliveryRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.delivery.DeliveryResponse;
import com.deliverytouroptimizer.deliverytouroptimizerv2.model.Delivery;
import com.deliverytouroptimizer.deliverytouroptimizerv2.model.Tour;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = { TourMapper.class })
public interface DeliveryMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tour", expression = "java(toTour(dto.tourId()))")
    Delivery toEntity(CreateDeliveryRequest dto);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "tour", expression = "java(toTour(request.tourId()))")
    void updateEntityFromDto(UpdateDeliveryRequest request, @MappingTarget Delivery delivery);
    DeliveryResponse toResponse(Delivery delivery);

    default Tour toTour(Long tourId) {
        if (tourId == null) return null;
        Tour tour = new Tour();
        tour.setId(tourId);
        return tour;
    }
}
