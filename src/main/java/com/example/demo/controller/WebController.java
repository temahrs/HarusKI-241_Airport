package com.example.demo.controller;

import com.example.demo.model.City;
import com.example.demo.repository.AirportRepository;
import com.example.demo.repository.CityRepository;
import com.example.demo.repository.FlightRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class WebController {

    private final CityRepository cityRepository;
    private final AirportRepository airportRepository;
    private final FlightRepository flightRepository;

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {
        model.addAttribute("cities", cityRepository.findAll());
        model.addAttribute("airports", airportRepository.findAll());
        model.addAttribute("flights", flightRepository.findAll());
        boolean isAdmin = request.isUserInRole("ADMIN");
        model.addAttribute("isAdmin", isAdmin);
        
        return "index";
    }

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
}