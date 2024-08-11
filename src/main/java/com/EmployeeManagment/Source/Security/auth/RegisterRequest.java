package com.EmployeeManagment.Source.Security.auth;


import com.EmployeeManagment.Source.Security.entities.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private  String firstname ;
    private String lastname ;
    private String email ;
    private String password ;
    private Roles role ;

}
