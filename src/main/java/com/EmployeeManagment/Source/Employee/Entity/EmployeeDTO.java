package com.EmployeeManagment.Source.Employee.Entity;

import com.EmployeeManagment.Source.Position.Entity.Position;
import com.EmployeeManagment.Source.Security.entities.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class EmployeeDTO {


    private Long id ;
    private String name ;
    private String surname ;
    private String email;
    private String position;
    @Lob
    @Column(name = "photo", columnDefinition = "MEDIUMBLOB")
    private byte[] photo ;
    private String function ;






}
