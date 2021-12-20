package com.jvs.interviewlandroute.utils;

import com.jvs.interviewlandroute.exceptions.NoPathException;
import com.jvs.interviewlandroute.models.Country;
import com.jvs.interviewlandroute.models.Region;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class BreadthFirstSearchTest {

    private static final Map<String, Country> COUNTRY_MAP = new HashMap<>();

    static {
        COUNTRY_MAP.put("e1", new Country("e1", Region.EUROPE, List.of("e2", "e3")));
        COUNTRY_MAP.put("e2", new Country("e2", Region.EUROPE, List.of("e1", "e3", "a1")));
        COUNTRY_MAP.put("e3", new Country("e3", Region.EUROPE, List.of("e1", "e2", "a2")));
        COUNTRY_MAP.put("e4", new Country("e4", Region.EUROPE, List.of()));
        COUNTRY_MAP.put("a1", new Country("a1", Region.ASIA, List.of("e2", "a3")));
        COUNTRY_MAP.put("a2", new Country("a2", Region.ASIA, List.of("e3", "a4")));
        COUNTRY_MAP.put("a3", new Country("a3", Region.ASIA, List.of("a1", "a4")));
        COUNTRY_MAP.put("a4", new Country("a4", Region.ASIA, List.of("a2", "a3", "a5")));
        COUNTRY_MAP.put("a5", new Country("a5", Region.ASIA, List.of("a4", "a6")));
        COUNTRY_MAP.put("a6", new Country("a6", Region.ASIA, List.of("a5")));
        COUNTRY_MAP.put("o1", new Country("o1", Region.ASIA, List.of("o2", "o3")));
        COUNTRY_MAP.put("o2", new Country("o2", Region.ASIA, List.of("o1", "o3")));
        COUNTRY_MAP.put("o3", new Country("o3", Region.ASIA, List.of("o1", "o2")));
        COUNTRY_MAP.put("o4", new Country("o4", Region.ASIA, List.of()));
    }

    @Test
    void testSameOriginAndDestination() {
        var country = "e1";
        Assertions.assertEquals(
                List.of(country),
                BreadthFirstSearch.paths(country, country, COUNTRY_MAP));
    }

    @Test
    void testDirectNeighbourPath() {
        var e1 = "e1";
        var e2 = "e2";
        Assertions.assertEquals(
                List.of(e1, e2),
                BreadthFirstSearch.paths(e1, e2, COUNTRY_MAP));
    }

    @Test
    void testDistantNeighbourPath() {
        var e1 = "e1";
        var e3 = "e3";
        Assertions.assertEquals(
                List.of(e1, e3),
                BreadthFirstSearch.paths(e1, e3, COUNTRY_MAP));
    }

    @Test
    void testDistantNeighbourLongPath() {
        var e1 = "e1";
        var a6 = "a6";
        Assertions.assertEquals(
                List.of(e1, "e3", "a2", "a4", "a5", a6),
                BreadthFirstSearch.paths(e1, a6, COUNTRY_MAP));
    }

    @Test
    void testIsolatedCountry() {
        Assertions.assertThrows(NoPathException.class, () -> BreadthFirstSearch.paths("o1", "o4", COUNTRY_MAP));
    }
}