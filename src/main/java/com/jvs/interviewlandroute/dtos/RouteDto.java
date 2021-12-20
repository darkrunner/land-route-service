package com.jvs.interviewlandroute.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record RouteDto(@JsonProperty("route") List<String> routes) {
}
