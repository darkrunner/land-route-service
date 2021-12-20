package com.jvs.interviewlandroute.services;

import com.jvs.interviewlandroute.dtos.RouteDto;
import com.jvs.interviewlandroute.models.Country;
import com.jvs.interviewlandroute.utils.BreadthFirstSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
@Service
public class RoutingService {

    @Autowired
    private CountryService countryService;
    private Map<String, Country> countriesHash;

    @PostConstruct
    public void initialize() {
        this.countriesHash = countryService.countries().stream()
                .collect(Collectors.toMap(Country::name, Function.identity()));
    }

    public RouteDto route(String origin, String destination) {
        return new RouteDto(BreadthFirstSearch.paths(origin, destination, countriesHash));
    }
}
