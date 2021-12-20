package com.jvs.interviewlandroute.utils;

import com.jvs.interviewlandroute.exceptions.NoPathException;
import com.jvs.interviewlandroute.models.Country;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BreadthFirstSearch {

    private static final String COUNTRY_UNKNOWN = "%s country %s unknown";
    private static final String COUNTRY_HAS_NO_BORDERS = "%s country %s has no borders";

    private BreadthFirstSearch() {
        //No-Ops
    }

    public static List<String> paths(String origin, String destination, Map<String, Country> countries) {

        var originCountry = Optional.ofNullable(countries.get(origin))
                .orElseThrow(() -> new NoPathException(String.format(COUNTRY_UNKNOWN, "Origin", origin)));

        var destinationCountry = Optional.ofNullable(countries.get(destination))
                .orElseThrow(() -> new NoPathException(String.format(COUNTRY_UNKNOWN, "Destination", destination)));

        if (!originCountry.region().connectedWith(destinationCountry.region())) {
            throw new NoPathException(String.format(
                    "%s (%s) is not connected with %s (%s) by land",
                    originCountry.region(), origin,
                    destinationCountry.region(), destination));
        }

        if (!origin.equals(destination)) {
            if (originCountry.borders().isEmpty()) {
                throw new NoPathException(String.format(COUNTRY_HAS_NO_BORDERS, "Origin", origin));
            }

            if (destinationCountry.borders().isEmpty()) {
                throw new NoPathException(String.format(COUNTRY_HAS_NO_BORDERS, "Destination", destination));
            }
        }

        var queue = new ArrayDeque<Country>();
        var visited = new HashMap<Country, Boolean>();
        var previous = new HashMap<Country, Country>();

        var currentCountry = originCountry;
        visited.put(currentCountry, true);
        queue.add(currentCountry);

        while (!queue.isEmpty()) {
            currentCountry = queue.remove();
            if (currentCountry.equals(destinationCountry)) {
                break;
            } else {
                for (var neighbour : currentCountry.borders()) {

                    var neighbourCountry = countries.get(neighbour);
                    if(!visited.containsKey(neighbourCountry)){
                        queue.add(neighbourCountry);
                        visited.put(neighbourCountry, true);
                        previous.put(neighbourCountry, currentCountry);
                        if (neighbourCountry.equals(destinationCountry)) {
                            currentCountry = neighbourCountry;
                            break;
                        }
                    }
                }
            }
        }

        if (!currentCountry.equals(destinationCountry)){
            throw new NoPathException("No path found");
        }

        return constructPath(destinationCountry, previous);
    }

    private static List<String> constructPath(Country destinationCountry, HashMap<Country, Country> previous) {
        List<String> path = new ArrayList<>();
        Country node = destinationCountry;
        while(node != null) {
            path.add(node.name());
            node = previous.get(node);
        }

        Collections.reverse(path);
        return path;
    }

}