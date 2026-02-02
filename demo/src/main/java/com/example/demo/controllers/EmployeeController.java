package com.example.demo.controllers;
import com.example.demo.OnUpdate;
import com.example.demo.dtos.request.EmployeeRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import com.example.demo.dtos.responce.EmployeeResponceDTO;
import com.example.demo.entites.Employee;
import com.example.demo.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService empService;
   @Value ("${image.folder}")
   private String imageFolder;
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
    @Operation(
            summary = "get emp by id",
            description = "all set",
            responses = {
                 @ApiResponse(responseCode = "200" ,description = "emp fetched ")
            }
    )
    public ResponseEntity<EmployeeResponceDTO> updateEmployee(@Validated(OnUpdate.class) @RequestBody @Valid EmployeeRequestDTO dto, @PathVariable long id) {

        return ResponseEntity.ok(empService.updateEmployee(dto,id));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeResponceDTO> partiallyUpdateEmployee(@RequestBody @Valid EmployeeRequestDTO dto,@PathVariable long id) {

        return ResponseEntity.ok(empService.partiallyUpdateEmployee(dto,id));
    }
    @DeleteMapping ("/{id}")
    public ResponseEntity  deleteEmployee(@PathVariable long id) {
        empService.deleteEmployee(id);
        return ResponseEntity.ok("deleted");
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<Resource>getimage(@PathVariable long id, ServletWebRequest request) throws IOException {

        String imageName="emp_"+id+".png";
        Path fullImagePath= Paths.get(imageFolder +"\\"+ imageName);
        Resource image=new UrlResource(fullImagePath.toUri());

        long lastModified=image.lastModified();

        if(request.checkNotModified(lastModified))
        {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .cacheControl(CacheControl.maxAge(1, TimeUnit.HOURS)).body(image);
    }

    @PostMapping("/{id}/image")
    public ResponseEntity<String> uploadImage (@PathVariable long id, @RequestParam("file")MultipartFile file) throws IOException{
       if(file.isEmpty())
       {
           return ResponseEntity.badRequest().body("file is empty");
       }
        Files.createDirectories(Paths.get(imageFolder));
        String fileName= "emp_"+id+".png";
        Path filePath =Paths.get(imageFolder, fileName);
        Files.write(filePath,file.getBytes());
        empService.updateImagePath(id,filePath.toString());
        return ResponseEntity.ok("image is uploaded");
    }
}



