package com.example.demo.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.NonNull;

import java.util.Date;

@Getter
@Setter
public class EmployeeRequestDTO {
    @NotBlank(message ="empty not allowed here")
    @Schema
    private String name;
    @NotBlank(message = "dept is not empty allowed here")
    @Schema
    private String department;
    @NotBlank(message = "dept is not empty allowed here")
    @Schema
    private Date hireDate;
}
