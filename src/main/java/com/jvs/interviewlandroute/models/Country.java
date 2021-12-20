package com.jvs.interviewlandroute.models;

import java.util.List;

public record Country(String name, Region region,List<String> borders) {
}
