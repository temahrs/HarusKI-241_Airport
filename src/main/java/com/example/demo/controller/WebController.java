package com.example.demo.controller;

import com.example.demo.model.Airport;
import com.example.demo.model.City;
import com.example.demo.model.Flight;
import com.example.demo.repository.AirportRepository;
import com.example.demo.repository.CityRepository;
import com.example.demo.repository.FlightRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WebController {

    private final CityRepository cityRepository;
    private final AirportRepository airportRepository;
    private final FlightRepository flightRepository;

    public WebController(CityRepository cityRepository, AirportRepository airportRepository, FlightRepository flightRepository) {
        this.cityRepository = cityRepository;
        this.airportRepository = airportRepository;
        this.flightRepository = flightRepository;
    }

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {
        model.addAttribute("cities", cityRepository.findAll());
        model.addAttribute("airports", airportRepository.findAll());
        model.addAttribute("flights", flightRepository.findAll());
        model.addAttribute("isAdmin", request.isUserInRole("ADMIN"));
        return "index";
    }

    //МІСТО
    @PostMapping("/cities/add")
    public String addCity(@RequestParam String name, HttpServletRequest request) {
        if (request.isUserInRole("ADMIN")) {
            City city = new City();
            city.setName(name);
            cityRepository.save(city);
        }
        return "redirect:/";
    }

    @PostMapping("/cities/delete/{id}")
    public String deleteCity(@PathVariable Long id, HttpServletRequest request) {
        if (request.isUserInRole("ADMIN")) {
            cityRepository.deleteById(id);
        }
        return "redirect:/";
    }

    // ТУТ АЕРОПОРТИ !!!
    @PostMapping("/airports/add")
    public String addAirport(@RequestParam String name, @RequestParam Long cityId, HttpServletRequest request) {
        if (request.isUserInRole("ADMIN")) {
            cityRepository.findById(cityId).ifPresent(city -> {
                Airport airport = new Airport();
                airport.setName(name);
                airport.setCity(city);
                airportRepository.save(airport);
            });
        }
        return "redirect:/";
    }

    @PostMapping("/airports/delete/{id}")
    public String deleteAirport(@PathVariable Long id, HttpServletRequest request) {
        if (request.isUserInRole("ADMIN")) {
            airportRepository.deleteById(id);
        }
        return "redirect:/";
    }

    // ТУТ РЕЙСИ 
    @PostMapping("/flights/add")
    public String addFlight(@RequestParam String flightNumber, @RequestParam String destination, @RequestParam Long airportId, HttpServletRequest request) {
        if (request.isUserInRole("ADMIN")) {
            airportRepository.findById(airportId).ifPresent(airport -> {
                Flight flight = new Flight();
                flight.setFlightNumber(flightNumber);
                flight.setDestination(destination);
                flight.setAirport(airport);
                flightRepository.save(flight);
            });
        }
        return "redirect:/";
    }

    @PostMapping("/flights/delete/{id}")
    public String deleteFlight(@PathVariable Long id, HttpServletRequest request) {
        if (request.isUserInRole("ADMIN")) {
            flightRepository.deleteById(id);
        }
        return "redirect:/";
    }
}