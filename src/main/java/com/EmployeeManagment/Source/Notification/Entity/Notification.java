package com.EmployeeManagment.Source.Notification.Entity;

import com.EmployeeManagment.Source.Employee.Entity.Employee;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Notification implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String message ;
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee ;
    private String type ;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date date ;
    private Boolean views ;


    public Notification(String message, Employee employee, Date date , String type){
        this.setMessage(message);
        this.setEmployee(employee);
        this.setDate(date);
        this.setViews(false);
        this.setType(type);
    }




    public Notification(String message, Employee e, Date date, boolean b, String type) {
        this.setMessage(message);
        this.setEmployee(e);
        this.setDate(date);
        this.setViews(b);
        this.setType(type);
    }
}
