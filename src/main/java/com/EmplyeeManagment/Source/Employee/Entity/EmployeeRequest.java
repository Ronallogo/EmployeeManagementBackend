package com.EmplyeeManagment.Source.Employee.Entity;

import com.EmplyeeManagment.Source.Position.Entity.Position;
import jakarta.persistence.*;
import lombok.*;

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
    private Date birthday ;
    private String phone ;
    private Long position;
    private String address ;
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
}
