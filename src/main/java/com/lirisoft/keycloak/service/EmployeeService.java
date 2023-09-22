//package com.lirisoft.keycloak.service;
//
//import com.lirisoft.keycloak.entity.Employee;
//import com.lirisoft.keycloak.repository.EmployeeRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//@Service
//public class EmployeeService {
//
//    @Autowired
//    private EmployeeRepository employeeRepository;
//
//    @PostConstruct
//    public void initializeEmployeeTable() {
//        employeeRepository.saveAll(
//                Stream.of(
//                        new Employee("Ram", 20000),
//                        new Employee("Shyam", 90000),
//                        new Employee("Rahul", 12000)
//                ).collect(Collectors.toList()));
//    }
//
//    public Employee getEmployee(int employeeId) {
//        return employeeRepository
//                .findById(employeeId)
//                .orElse(null);
//    }
//
//    public List<Employee> getAllEmployees() {
//        return employeeRepository
//                .findAll();
//    }
//}
