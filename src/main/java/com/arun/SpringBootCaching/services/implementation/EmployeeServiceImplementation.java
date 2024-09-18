package com.arun.SpringBootCaching.services.implementation;

import com.arun.SpringBootCaching.dto.EmployeeDto;
import com.arun.SpringBootCaching.entities.Employee;
import com.arun.SpringBootCaching.exceptions.ResourceNotFoundException;
import com.arun.SpringBootCaching.repositories.EmployeeRepository;
import com.arun.SpringBootCaching.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImplementation implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final ModelMapper modelMapper;

    private final String CACHE_NAME = "employee";


    @Override
    @CachePut(cacheNames = CACHE_NAME,key = "#result.id")
    public EmployeeDto createNewEmployee(EmployeeDto employeeDto) {

        log.info("Creating new employee : {}", employeeDto);
        Optional<Employee> employee = employeeRepository.findByEmail(employeeDto.getEmail());
        if(employee.isPresent()) {
            log.error("User already exists with email : {}", employeeDto.getEmail());
            throw new RuntimeException("User Already Exists with given email"+employeeDto.getEmail());
        }
        Employee employeeToBeSaved = modelMapper.map(employeeDto, Employee.class);
        Employee savedEmployee = employeeRepository.save(employeeToBeSaved);
        log.info("Employee saved successfully : {}", savedEmployee);
        return modelMapper.map(savedEmployee, EmployeeDto.class);
    }

    @Override
    @Cacheable(cacheNames = CACHE_NAME,key = "#id")
    public EmployeeDto getEmployeeById(Long id) {
        log.info("Finding Employee by id : {}", id);
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Not Found with given id : " + id));
        log.info("Employee found with id : {}", id);
        return modelMapper.map(employee, EmployeeDto.class);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        log.info("Finding all employees ");
        List<Employee> allEmployees = employeeRepository.findAll();
        log.info("Employees found");
        List<EmployeeDto> savedEmployees= allEmployees.stream().map(employee -> modelMapper.map(employee, EmployeeDto.class))
                .collect(Collectors.toList());
        return savedEmployees;

    }

    @Override
    @CachePut(cacheNames = CACHE_NAME,key = "#id")
    public EmployeeDto updateEmployee(EmployeeDto employeeDto, Long id) {
        log.info("Updating Employee with id : {}", id);
        Employee employee = employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee With Given Id not Found"+id));
        employee.setEmail(employeeDto.getEmail());
        employee.setName(employeeDto.getName());
        employee.setSalary(employeeDto.getSalary());
        Employee savedEmployee = employeeRepository.save(employee);
        log.info("Employee saved successfully : {}", savedEmployee);
        return modelMapper.map(savedEmployee,EmployeeDto.class);

    }

    @Override
    @CacheEvict(cacheNames = CACHE_NAME,key = "#id")
    public void deleteEmployee(Long id) {
        log.info("Deleting Employee with id : {}", id);
        employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee With Given Id not Found"+id));
        employeeRepository.deleteById(id);
        log.info("Employee deleted successfully : {}", id);
    }
}
