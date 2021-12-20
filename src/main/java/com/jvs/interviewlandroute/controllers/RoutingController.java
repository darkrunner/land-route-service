package com.jvs.interviewlandroute.controllers;

import com.jvs.interviewlandroute.dtos.RouteDto;
import com.jvs.interviewlandroute.services.RoutingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Size;

@RestController
@Validated
@RequestMapping("/routing")
public class RoutingController {
    @Autowired
    private RoutingService routingService;

    @GetMapping("{origin}/{destination}")
    public ResponseEntity<RouteDto> findPath(
            @PathVariable @Size(min = 3, max = 3, message = "Origin name should comply to cca3 standard") String origin,
          @PathVariable @Size(min = 3, max = 3, message = "Destination name should comply to cca3 standard") String destination) {
        return ResponseEntity.ok(routingService.route(origin, destination));
    }
}
