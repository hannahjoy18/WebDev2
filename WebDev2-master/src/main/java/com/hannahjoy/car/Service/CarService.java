package com.hannahjoy.car.Service;

import com.hannahjoy.car.DTO.CarDTO;
import com.hannahjoy.car.Exceptions.ResourceNotFoundException;
import com.hannahjoy.car.Model.Car;
import com.hannahjoy.car.Repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository){
        this.carRepository = carRepository;
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car save(CarDTO carDTO) {
        Car newCar = new Car();
        newCar.setMake(carDTO.getMake());
        newCar.setModel(carDTO.getModel());
        newCar.setYear(carDTO.getYear());
        newCar.setColor(carDTO.getColor());
        newCar.setBodyType(carDTO.getBodyType());
        newCar.setEngineType(carDTO.getEngineType());
        newCar.setTransmissionType(carDTO.getTransmissionType());
        newCar.setLicensePlate(carDTO.getLicensePlate());

        return carRepository.save(newCar);
    }

    public Car getCarById(int id) {
        return carRepository.findById(id)
                .orElse(null);
    }

    public Car updateCar(int id, CarDTO carDTO) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car", id));

        car.setMake(carDTO.getMake());
        car.setModel(carDTO.getModel());
        car.setYear(carDTO.getYear());
        car.setColor(carDTO.getColor());
        car.setBodyType(carDTO.getBodyType());
        car.setEngineType(carDTO.getEngineType());
        car.setTransmissionType(carDTO.getTransmissionType());
        car.setLicensePlate(carDTO.getLicensePlate());

        carRepository.save(car);
        return car;
    }

    public void delete(int id) {
        carRepository.deleteById(id);
    }
}

