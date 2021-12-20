package com.jvs.interviewlandroute.models;

import java.util.Arrays;
import java.util.Set;

public enum Region {
    AFRICA,
    AMERICAS,
    ANTARCTIC,
    ASIA,
    EUROPE,
    OCEANIA;

    private static final Set<Region> CONTINENTAL = Set.of(AFRICA, ASIA, EUROPE);

    public boolean connectedWith(Region region) {
        return this == region || (CONTINENTAL.contains(this) && CONTINENTAL.contains(region));
    }

    public static Region valueOfName(String regionName) {
        return Arrays.stream(values()).filter(region -> region.name().equalsIgnoreCase(regionName)).findAny().orElse(null);
    }
}