package com.jvs.interviewlandroute.clients;

import java.util.List;

import com.jvs.interviewlandroute.dtos.CountryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="rest-countries", url="${rest.countries-client.url}")
public interface CountryClient {

    @GetMapping(path = "/master/countries.json")
    List<CountryDto> countries();
}

