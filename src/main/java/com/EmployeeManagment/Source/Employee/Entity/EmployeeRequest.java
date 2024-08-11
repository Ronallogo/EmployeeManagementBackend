package com.EmployeeManagment.Source.Employee.Entity;

import lombok.*;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeRequest {

    private Long id ;
    private String name ;
    private String surname ;
    private String email;
    private LocalDate birthday ;
    private String phone ;
    private Long position;
    private String address ;
    ////// constructor personalize for creation a employee
    public EmployeeRequest(
            String name ,
            String surname ,
            String email ,
            String address,
            LocalDate birthday ,
            String phone ,
            Long position
    ){
       this.setName(name);
       this.setSurname(surname);
       this.setBirthday(birthday);
       this.setEmail(email);
       this.setPhone(phone);
       this.setPosition(position);
       this.setAddress(address);

    }
}
