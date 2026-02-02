package com.example.demo.services;

import com.example.demo.dtos.request.EmployeeRequestDTO;
import jakarta.validation.Valid;
import com.example.demo.dtos.responce.EmployeeResponceDTO;
import com.example.demo.entites.Employee;
import com.example.demo.repos.EmployeeRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepo employeeRepo;

    @Autowired
    private ModelMapper modelMapper;
    @PersistenceContext
    private EntityManager entityManager;

    //Constructor Injection
    @Autowired
    public EmployeeService(EmployeeRepo employeeRepo)
    {
        this.employeeRepo=employeeRepo;

    }

    public List<EmployeeResponceDTO> getallEmployees ( ) {

        List<Employee> allEmp = employeeRepo.findAll();

        List<EmployeeResponceDTO> dtos = allEmp
                .stream()
                .map(a -> modelMapper.map(a, EmployeeResponceDTO.class))
                .collect(Collectors.toList());
        return dtos;
    }
    public EmployeeResponceDTO getEmployeesById (long id ) {

        Employee employee = employeeRepo.findById(id).orElse(null);

        return modelMapper.map(employee,EmployeeResponceDTO.class);
    }

    public  EmployeeResponceDTO  createEmployee (EmployeeRequestDTO dto) {

        Employee newEmp =modelMapper.map(dto,Employee.class);
        newEmp.setImagePath(null);
        employeeRepo.save(newEmp);

        return modelMapper.map(dto,EmployeeResponceDTO.class);
    }
    public  EmployeeResponceDTO  updateEmployee (EmployeeRequestDTO dto,long id) {

        Employee emp=employeeRepo.findById(id).orElse(null);
        if(emp!=null) {
           Employee updatedEmp= modelMapper.map(dto,Employee.class);
            updatedEmp.setId(emp.getId());
             employeeRepo.save(updatedEmp);
        }
        else {
            //runtime ex
        }
        return modelMapper.map(dto,EmployeeResponceDTO.class);
    }

    public void deleteEmployee (Long id) {

        Employee emp = employeeRepo.findById(id).orElse(null);
        if(emp!=null) {
            employeeRepo.delete(emp);
        }else{
            //runtime ex
        }

        return ;
    }

    public void updateImagePath(Long id, String path)
    {
        Employee emp = employeeRepo.findById(id).orElse(null);
        if(emp!=null) {
            emp.setImagePath(path);
            employeeRepo.save(emp);
        }else{
            //runtime ex
        }
    }
    public  EmployeeResponceDTO   partiallyUpdateEmployee (EmployeeRequestDTO dto,long id) {

        Employee emp=employeeRepo.findById(id).orElse(null);
        if(emp!=null) {
             modelMapper.map(dto,emp);
            employeeRepo.save(emp);
        }
        else {
            //runtime ex
        }
        return modelMapper.map(dto,EmployeeResponceDTO.class);
    }

}
