package com.EmployeeManagment.Source.Employee.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO1 {
    private Long id ;
    private String name ;
    private String surname ;
    private String email;
    @Lob
    @Column(name = "photo", columnDefinition = "MEDIUMBLOB")
    private byte[] photo;
    private String position;


}
