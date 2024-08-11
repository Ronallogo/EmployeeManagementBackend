package com.EmployeeManagment.Source.TimeOff.Entity;

import com.EmployeeManagment.Source.Employee.Entity.Employee;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "TimeOff")
public class TimeOff implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private LocalDate beginning ;
    private LocalDate end ;
    private String type ;
    private boolean status ;
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee ;


    ////// constructor personalized for TimeOff creation
    public TimeOff(
        LocalDate beginning ,
        LocalDate end ,
        String type ,
        boolean status ,
        Employee employee
    ){
        this.setBeginning(beginning);
        this.setEmployee(employee);
        this.setEnd(end);
        this.setType(type);
        this.setStatus(status);

    }
}
