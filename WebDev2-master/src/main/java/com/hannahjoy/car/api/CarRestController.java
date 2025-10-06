package com.hannahjoy.car.api;

import com.hannahjoy.car.DTO.CarDTO;
import com.hannahjoy.car.Model.Car;
import com.hannahjoy.car.Service.CarService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CarRestController {

    private final CarService carService;

    public CarRestController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/cars")
    public List<Car> getCars() {
        return carService.getAllCars();
    }

    @GetMapping("/{id}")
    public Car getCarById(@PathVariable int id) {
        return carService.getCarById(id);
    }

    @PostMapping("/cars")
    public Car createCar(@Valid @RequestBody CarDTO carDTO) {
        carService.save(carDTO);
        return carService.getCarById(carDTO.getId());
    }

    @PutMapping("/cars/{id}")
    public Car updateCar(@PathVariable int id, @Valid @RequestBody CarDTO carDTO) {
        carService.update(id, carDTO);
        return carService.getCarById(id);
    }

    @DeleteMapping("/cars/{id}")
    public Car deleteCar(@PathVariable int id) {
        carService.delete(id);
        return carService.getCarById(id);
    }
}
