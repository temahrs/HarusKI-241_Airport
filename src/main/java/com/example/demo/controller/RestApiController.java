
package com.example.demo.controller;

import com.example.demo.model.City;
import com.example.demo.model.Airport;
import com.example.demo.model.Flight;
import com.example.demo.repository.CityRepository;
import com.example.demo.repository.AirportRepository;
import com.example.demo.repository.FlightRepository;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RestApiController {

    private final CityRepository cityRepository;
    private final AirportRepository airportRepository;
    private final FlightRepository flightRepository;

    public RestApiController(CityRepository cityRepository,
                             AirportRepository airportRepository,
                             FlightRepository flightRepository) {
        this.cityRepository = cityRepository;
        this.airportRepository = airportRepository;
        this.flightRepository = flightRepository;
    }

    // Список міст
    @GetMapping("/cities")
    public List<Map<String, Object>> getCities() {
        return cityRepository.findAll().stream()
            .map(city -> Map.<String, Object>of(
                "id", city.getId(),
                "name", city.getName()
            ))
            .toList();
    }

    // Список аеропортів
    @GetMapping("/airports")
    public List<Map<String, Object>> getAirports() {
        return airportRepository.findAll().stream()
            .map(airport -> Map.<String, Object>of(
                "id", airport.getId(),
                "name", airport.getName(),
                "city", airport.getCity().getName()
            ))
            .toList();
    }

    // Список рейсів
    @GetMapping("/flights")
    public List<Map<String, Object>> getFlights() {
        return flightRepository.findAll().stream()
            .map(flight -> Map.<String, Object>of(
                "id", flight.getId(),
                "number", flight.getFlightNumber(),
                "destination", flight.getDestination(),
                "airport", flight.getAirport().getName()
            ))
            .toList();
    }
}
