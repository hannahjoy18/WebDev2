package com.example.employee.controller;

import com.example.employee.model.Employee;
import com.example.employee.repository.EmployeeRepository;
import com.example.employee.DTO.EmployeeDTO;
import com.example.employee.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // List all employees
    @GetMapping("/")
    public String index(Model model) {
        List<Employee> employees = employeeRepository.findAll();
        model.addAttribute("employees", employees);
        return "index"; // index.html
    }

    // Show add form
    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("employee", new EmployeeDTO());
        return "add"; // add.html
    }

    // Save new employee
    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") @Valid EmployeeDTO employeeDTO,
                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "add";
        }

        Optional<Employee> existing = employeeRepository.findByEmail(employeeDTO.getEmail());
        if (existing.isPresent()) {
            bindingResult.rejectValue("email", "error.employee", "Email already exists");
            return "add";
        }

        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());
        employeeRepository.save(employee);

        return "redirect:/";
    }

    // Show edit form
    @GetMapping("/edit")
    public String editEmployee(@RequestParam Long id, Model model) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", id));

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setEmail(employee.getEmail());

        model.addAttribute("employee", employeeDTO);
        return "edit"; // edit.html
    }

    // Update employee
    @PostMapping("/update")
    public String updateEmployee(@ModelAttribute("employee") @Valid EmployeeDTO employeeDTO,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "edit";
        }

        Optional<Employee> existingByEmail = employeeRepository.findByEmail(employeeDTO.getEmail());
        if (existingByEmail.isPresent() && !existingByEmail.get().getId().equals(employeeDTO.getId())) {
            bindingResult.rejectValue("email", "error.employee", "Email already exists");
            return "edit";
        }

        Employee employee = employeeRepository.findById(employeeDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee", employeeDTO.getId()));

        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());
        employeeRepository.save(employee);

        return "redirect:/";
    }

    // Delete employee
    @GetMapping("/delete")
    public String delete(@RequestParam Long id) {
        employeeRepository.deleteById(id);
        return "redirect:/";
    }

    // Logout page
    @GetMapping("/logout")
    public String logoutPage() {
        return "logout"; // logout.html
    }
}
