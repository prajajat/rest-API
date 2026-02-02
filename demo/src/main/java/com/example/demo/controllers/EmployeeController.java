package com.example.demo.controllers;
import com.example.demo.dtos.request.EmployeeRequestDTO;
import jakarta.validation.Valid;
import com.example.demo.dtos.responce.EmployeeResponceDTO;
import com.example.demo.entites.Employee;
import com.example.demo.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService empService;

   @GetMapping("/all")
    public ResponseEntity<List<EmployeeResponceDTO>>getEmployees() {

        return ResponseEntity.ok(empService.getallEmployees());
    }
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponceDTO>getEmployee(@PathVariable long id) {

        return ResponseEntity.ok(empService.getEmployeesById(id));
    }
    @PostMapping("/")
    public ResponseEntity<EmployeeResponceDTO> createEmployee(@RequestBody @Valid EmployeeRequestDTO dto) {

        return ResponseEntity.ok(empService.createEmployee(dto));
    }
    @PutMapping ("/{id}")
    public ResponseEntity<EmployeeResponceDTO> updateEmployee(@RequestBody @Valid EmployeeRequestDTO dto,@PathVariable long id) {

        return ResponseEntity.ok(empService.updateEmployee(dto,id));
    }
//    @PatchMapping("/{id}")
//    public ResponseEntity<EmployeeResponceDTO> partiallyUpdateEmployee(,@PathVariable long id) {
//
//        return ResponseEntity.ok(empService.partiallyUpdateEmployee(dto));
//    }
    @DeleteMapping ("/{id}")
    public ResponseEntity  deleteEmployee(@PathVariable long id) {
        empService.deleteEmployee(id);
        return ResponseEntity.ok("deleted");
    }

}



