package com.jvs.interviewlandroute.services;

import com.jvs.interviewlandroute.clients.CountryClient;
import com.jvs.interviewlandroute.dtos.CountryDto;
import com.jvs.interviewlandroute.models.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {
    @Autowired
    private CountryClient countryClient;

    public List<Country> countries() {
        return countryClient.countries().stream().map(CountryDto::toCountry).toList();
    }
}
