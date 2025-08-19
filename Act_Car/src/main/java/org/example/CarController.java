package org.example;

import com.example.carapp.model.Car;
import com.example.carapp.repository.CarRepository;
import com.example.carapp.service.CsvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CarController {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CsvService csvService;

    @GetMapping("/cars")
    public String getAllCars(Model model) {
        var cars = carRepository.findAll();
        model.addAttribute("cars", cars);
        return "cars";
    }

    @GetMapping("/add")
    public String showForm(Model model) {
        model.addAttribute("car", new Car());
        return "add_car";
    }

    @PostMapping("/add")
    public String addCar(@ModelAttribute Car car) {
        carRepository.save(car);
        csvService.saveCarsToCSV(carRepository.findAll()); // save to CSV after insert
        return "redirect:/cars";
    }
}
