package com.EmployeeManagment.Source.Security.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter

public class UserResponse {
    private Long id ;
    private String firstname ;
    private String lastname ;
    private String email;
    private String role ;
}
