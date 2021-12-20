package com.jvs.interviewlandroute.dtos;

import com.jvs.interviewlandroute.models.Country;
import com.jvs.interviewlandroute.models.Region;

import java.util.List;

public record CountryDto(String cca3, String region, List<String> borders){
    public Country toCountry() {
        return new Country(cca3(), Region.valueOfName(region()), borders());
    }
}
