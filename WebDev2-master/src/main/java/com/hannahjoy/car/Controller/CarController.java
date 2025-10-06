package com.hannahjoy.car.Controller;

import com.hannahjoy.car.DTO.CarDTO;
import com.hannahjoy.car.Exceptions.ResourceNotFoundException;
import com.hannahjoy.car.Model.Car;
import com.hannahjoy.car.Repository.CarRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CarController {

    private final CarRepository carRepository;

    public CarController(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @GetMapping("/")
    public String index(Model model){
        List<Car> cars = carRepository.findAll();
        model.addAttribute("cars", cars);
        return "index";
    }

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("car", new CarDTO());
        return "add";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("car") @Valid CarDTO car, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("car", car);
            return "add";
        }

        Car newCar = new Car();
        newCar.setMake(car.getMake());
        newCar.setModel(car.getModel());
        newCar.setYear(car.getYear());
        newCar.setColor(car.getColor());
        newCar.setBodyType(car.getBodyType());
        newCar.setEngineType(car.getEngineType());
        newCar.setTransmissionType(car.getTransmissionType());
        newCar.setLicensePlate(car.getLicensePlate());

        carRepository.save(newCar);
        return "redirect:/";
    }

    // Edit car form
    @GetMapping("/edit")
    public String edit(@RequestParam("id") int id, Model model){
        Car car = carRepository.findById(id).orElse(new Car());
        CarDTO dto = new CarDTO();
        dto.setId(car.getId());
        dto.setMake(car.getMake());
        dto.setModel(car.getModel());
        dto.setYear(car.getYear());
        dto.setColor(car.getColor());
        dto.setBodyType(car.getBodyType());
        dto.setEngineType(car.getEngineType());
        dto.setTransmissionType(car.getTransmissionType());
        dto.setLicensePlate(car.getLicensePlate());

        model.addAttribute("car", dto);
        return "edit";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("car") @Valid CarDTO car, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("car", car);
            return "add";
        }

        Car existingCar = carRepository.findById(car.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Car", car.getId()));

        existingCar.setMake(car.getMake());
        existingCar.setModel(car.getModel());
        existingCar.setYear(car.getYear());
        existingCar.setColor(car.getColor());
        existingCar.setBodyType(car.getBodyType());
        existingCar.setEngineType(car.getEngineType());
        existingCar.setTransmissionType(car.getTransmissionType());
        existingCar.setLicensePlate(car.getLicensePlate());

        carRepository.save(existingCar);
        return "redirect:/";
    }

    // Delete car
    @GetMapping("/delete")
    public String delete(@RequestParam("id") int id){
        carRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/view")
    public String view(@RequestParam int id, Model model){
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car", id));
        model.addAttribute("car", car);
        return "view";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }
        return "redirect:/login";
    }
}
