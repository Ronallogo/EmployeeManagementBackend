package com.EmployeeManagment.Source.Repartition.Entities;


import com.EmployeeManagment.Source.Employee.Entity.Employee;
import com.EmployeeManagment.Source.Task_Scheduled.Entity.TaskScheduled;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;


@Data
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Repartition")
public class Repartition implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee ;
    @ManyToOne
    @JoinColumn(name = "taskScheduled_id", nullable = false)
    private TaskScheduled taskScheduled ;

    private String function ;





    public Repartition(Employee e , TaskScheduled taskScheduled , String function){
        this.setEmployee(e) ;
        this.setFunction(function);
        this.setTaskScheduled(taskScheduled);
    }




}
