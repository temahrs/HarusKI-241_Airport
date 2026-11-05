package com.example.demo.service;

import com.example.demo.model.Airport;
import com.example.demo.model.City;
import com.example.demo.model.Flight;
import com.example.demo.model.User;
import com.example.demo.repository.AirportRepository;
import com.example.demo.repository.CityRepository;
import com.example.demo.repository.FlightRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
@RequiredArgsConstructor
@Slf4j 
public class DataInitializationService implements CommandLineRunner {

    private final CityRepository cityRepository;
    private final AirportRepository airportRepository;
    private final FlightRepository flightRepository;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("Перевірка та ініціалізація початкових даних...");

        if (userRepository.count() == 0) {
            log.info("Створення користувачів...");
            String adminPassword = Base64.getEncoder().encodeToString("admin123".getBytes());
            String userPassword = Base64.getEncoder().encodeToString("user123".getBytes());

            userRepository.save(new User(null, "admin", adminPassword, "ROLE_ADMIN"));
            userRepository.save(new User(null, "user", userPassword, "ROLE_USER"));
        }

        if (cityRepository.count() == 0) {
            log.info("Заповнення бази тестовими даними...");

            City kyiv = new City(null, "Київ", null);
            City lviv = new City(null, "Львів", null);
            cityRepository.save(kyiv);
            cityRepository.save(lviv);

            Airport boryspil = new Airport(null, "Бориспіль ", kyiv, null);
            Airport zhuliany = new Airport(null, "Жуляни ", kyiv, null);
            Airport lwo = new Airport(null, "Львів ", lviv, null);
            airportRepository.save(boryspil);
            airportRepository.save(zhuliany);
            airportRepository.save(lwo);

            flightRepository.save(new Flight(null, "A11", "Лондон", boryspil));
            flightRepository.save(new Flight(null, "P22", "Париж", boryspil));
            flightRepository.save(new Flight(null, "W33", "Варшава", zhuliany));
            flightRepository.save(new Flight(null, "F44", "Рим", lwo));
            
            log.info("Ініціалізація даних завершена");
        } else {
            log.info("База даних проініціалізована...");
        }
    }
}