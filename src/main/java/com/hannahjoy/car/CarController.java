package com.hannahjoy.car;

import com.hannahjoy.car.Car;
import com.hannahjoy.car.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("/")
    public String viewCars(Model model) {
        model.addAttribute("cars", carService.getAllCars());
        return "cars";  // your Thymeleaf template for listing cars
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("car", new Car());
        return "add_car";  // your Thymeleaf form for adding a car
    }

    @PostMapping("/add")
    public String addCar(@ModelAttribute Car car) {
        carService.addCar(car);
        return "redirect:/";
    }

    // Show edit form for a car by ID
    @GetMapping("/edit")
    public String showEditForm(@RequestParam int id, Model model) {
        Car car = carService.getCar(id);
        if (car == null) {
            model.addAttribute("error", "Car not found");
            return "error";  // create error.html or handle gracefully
        }
        model.addAttribute("car", car);
        return "edit_car";  // your Thymeleaf form for editing a car
    }

    // Handle update after editing
    @PostMapping("/update")
    public String updateCar(@ModelAttribute Car car) {
        carService.updateCar(car.getCarID(), car);
        return "redirect:/";
    }

    // Delete a car by ID
    @GetMapping("/delete")
    public String deleteCar(@RequestParam int id) {
        carService.deleteCar(id);
        return "redirect:/";
    }
}
