package com.jvs.interviewlandroute.services;

import com.jvs.interviewlandroute.exceptions.NoPathException;
import com.jvs.interviewlandroute.models.Country;
import com.jvs.interviewlandroute.models.Region;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = RoutingService.class)
class RoutingServiceImplTest {

    @Autowired
    private RoutingService routingService;

    @MockBean
    private CountryService countryService;

    @BeforeEach
    private void beforeEach() {
        Mockito.clearInvocations(countryService);
    }

    @Test
    void testSameOriginAndDestination() {
        Mockito.when(countryService.countries()).thenReturn(List.of(
                new Country("e1", Region.EUROPE, List.of("e2", "e3")),
                new Country("e2", Region.EUROPE, List.of("e1", "e3")),
                new Country("e3", Region.EUROPE, List.of("e1", "e2"))));

        routingService.initialize();

        Assertions.assertEquals(List.of("e1"),
                routingService.route("e1", "e1").routes());
    }


    @Test
    void testUnknownDestination() {
        Mockito.when(countryService.countries()).thenReturn(List.of(
                new Country("e1", Region.EUROPE, List.of("e2", "e3")),
                new Country("e2", Region.EUROPE, List.of("e1", "e3")),
                new Country("e3", Region.EUROPE, List.of("e1", "e2"))));

        routingService.initialize();

        var exception = Assertions.assertThrows(NoPathException.class,
                () -> routingService.route("e1", "BLA"));
        Assertions.assertEquals("Destination country BLA unknown",
                exception.getMessage());
    }

    @Test
    void testIsolatedOrigin() {
        Mockito.when(countryService.countries()).thenReturn(List.of(
                new Country("e1", Region.EUROPE, List.of()),
                new Country("e2", Region.EUROPE, List.of("e3")),
                new Country("e3", Region.EUROPE, List.of("e2"))));

        routingService.initialize();

        var exception = Assertions.assertThrows(NoPathException.class,
                () -> routingService.route("e1", "e3"));
        Assertions.assertEquals("Origin country e1 has no borders", exception.getMessage());
    }

    @Test
    void testIsolatedDestination() {
        Mockito.when(countryService.countries()).thenReturn(List.of(
                new Country("e1", Region.EUROPE, List.of("e2")),
                new Country("e2", Region.EUROPE, List.of("e1")),
                new Country("e3", Region.EUROPE, List.of())));

        routingService.initialize();

        var exception = Assertions.assertThrows(NoPathException.class,
                () -> routingService.route("e1", "e3"));
        Assertions.assertEquals("Destination country e3 has no borders", exception.getMessage());
    }

    @Test
    void testUnknownOrigin() {

        Mockito.when(countryService.countries()).thenReturn(List.of(
                new Country("e1", Region.EUROPE, List.of("e2", "e3")),
                new Country("e2", Region.EUROPE, List.of("e1", "e3")),
                new Country("e3", Region.EUROPE, List.of("e1", "e2"))));

        routingService.initialize();

        var exception = Assertions.assertThrows(NoPathException.class,
                () -> routingService.route("BLA", "e3"));
        Assertions.assertEquals("Origin country BLA unknown", exception.getMessage());
    }


    @Test
    void testSameIsolatedOriginAndDestination() {
        Mockito.when(countryService.countries()).thenReturn(List.of(
                new Country("e1", Region.EUROPE, List.of()),
                new Country("e2", Region.EUROPE, List.of()),
                new Country("e3", Region.EUROPE, List.of())));

        routingService.initialize();

        Assertions.assertEquals(List.of("e1"),
                routingService.route("e1", "e1").routes());
    }

    @Test
    void testNonContinentalRoute() {
        Mockito.when(countryService.countries()).thenReturn(List.of(
                new Country("e1", Region.EUROPE, List.of()),
                new Country("a1", Region.ANTARCTIC, List.of())));

        routingService.initialize();

        var exception = Assertions.assertThrows(NoPathException.class,
                () -> routingService.route("e1", "a1"));
        Assertions.assertEquals("EUROPE (e1) is not connected with ANTARCTIC (a1) by land",
                exception.getMessage());
    }

    @Test
    void testSampleRoute() {
        Mockito.when(countryService.countries()).thenReturn(List.of(
                new Country("a1", Region.AMERICAS, List.of("a2")),
                new Country("a2", Region.AMERICAS, List.of("a1", "a3")),
                new Country("a3", Region.AMERICAS, List.of("a2", "a4")),
                new Country("a4", Region.AMERICAS, List.of("a3"))));

        routingService.initialize();

        Assertions.assertEquals(List.of("a1", "a2", "a3", "a4"),
                routingService.route("a1", "a4").routes());
    }
}