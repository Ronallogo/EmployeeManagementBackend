package com.EmployeeManagment.Source.Employee.Entity;

import com.EmployeeManagment.Source.Security.entities.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;


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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date birthday ;
    private String phone ;
    private Long position;
    private String address ;
    private User  user;
    ////// constructor personalize for creation a employee
    public EmployeeRequest(
            String name ,
            String surname ,
            String email ,
            String address,
            Date birthday ,
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



    public EmployeeRequest(
            String name ,
            String surname ,
            String email ,
            String address,
            Date birthday ,
            String phone ,
            Long position ,
            User user
    ){
        this.setName(name);
        this.setSurname(surname);
        this.setBirthday(birthday);
        this.setEmail(email);
        this.setPhone(phone);
        this.setPosition(position);
        this.setAddress(address);;
        this.setUser(user);

    }
}
