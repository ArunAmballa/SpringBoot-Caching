package com.arun.SpringBootCaching.services;

import com.arun.SpringBootCaching.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {

    public EmployeeDto createNewEmployee(EmployeeDto employeeDto);

    public EmployeeDto getEmployeeById(Long id);

    public List<EmployeeDto> getAllEmployees();

    public EmployeeDto updateEmployee(EmployeeDto employeeDto, Long id);

    public void deleteEmployee(Long id);


}
