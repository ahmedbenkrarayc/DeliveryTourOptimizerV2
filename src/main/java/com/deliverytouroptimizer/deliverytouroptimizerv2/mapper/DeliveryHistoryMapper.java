package com.deliverytouroptimizer.deliverytouroptimizerv2.mapper;

import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.deliveryhistory.CreateDeliveryHistoryRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.deliveryhistory.UpdateDeliveryHistoryRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.deliveryhistory.DeliveryHistoryResponse;
import com.deliverytouroptimizer.deliverytouroptimizerv2.model.DeliveryHistory;
import org.mapstruct.*;

@Mapper(componentModel="spring")
public interface DeliveryHistoryMapper {
    @Mapping(target = "delivery.id", source = "deliveryId")
    DeliveryHistory toEntity(CreateDeliveryHistoryRequest request);

    @Mapping(target = "delivery.id", source = "deliveryId")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UpdateDeliveryHistoryRequest request, @MappingTarget DeliveryHistory deliveryHistory);

    @Mapping(source = "delivery.id", target = "deliveryId")
    DeliveryHistoryResponse toResponse(DeliveryHistory entity);
}
