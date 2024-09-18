package com.arun.SpringBootCaching.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDto implements Serializable {

    private Long id;

    private String name;

    private Double salary;

    private String email;

}
