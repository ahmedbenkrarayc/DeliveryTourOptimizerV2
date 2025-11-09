package com.deliverytouroptimizer.deliverytouroptimizerv2.controller;

import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.tour.CreateTourRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.request.tour.UpdateTourRequest;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.delivery.DeliveryResponse;
import com.deliverytouroptimizer.deliverytouroptimizerv2.dto.response.tour.TourResponse;
import com.deliverytouroptimizer.deliverytouroptimizerv2.service.TourService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tour")
@RequiredArgsConstructor
public class TourController {
    private final TourService tourService;

    @GetMapping
    public ResponseEntity<List<TourResponse>> getAll(){
        return ResponseEntity.ok(tourService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TourResponse> getById(@PathVariable Long id){
        return ResponseEntity.ok(tourService.getById(id));
    }

    @PostMapping
    public ResponseEntity<TourResponse> create(@Valid @RequestBody CreateTourRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tourService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TourResponse> update(@PathVariable Long id, @Valid @RequestBody UpdateTourRequest request){
        return ResponseEntity.ok(tourService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        tourService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/optimize/tour/{id}")
    public ResponseEntity<List<DeliveryResponse>> optimize(@PathVariable Long id){
        return ResponseEntity.ok(tourService.optimizeTour(id));
    }
}
