package com.arun.SpringBootCaching.controllers;


import com.arun.SpringBootCaching.dto.EmployeeDto;
import com.arun.SpringBootCaching.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;


    @PostMapping
    public ResponseEntity<EmployeeDto> createNewEmployee(@RequestBody EmployeeDto employeeDto){
        EmployeeDto newEmployee = employeeService.createNewEmployee(employeeDto);
        return new ResponseEntity<>(newEmployee, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id){
        EmployeeDto employeeById = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(employeeById, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees(){
        List<EmployeeDto> allEmployees = employeeService.getAllEmployees();
        return new ResponseEntity<>(allEmployees,HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public void deleteEmployeeById(@PathVariable Long id){
        employeeService.deleteEmployee(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody EmployeeDto employeeDto,@PathVariable Long id){
        EmployeeDto updateEmployee = employeeService.updateEmployee(employeeDto, id);
        return new ResponseEntity<>(updateEmployee, HttpStatus.OK);
    }




}
