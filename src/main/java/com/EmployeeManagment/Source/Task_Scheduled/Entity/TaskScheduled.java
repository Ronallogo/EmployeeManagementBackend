package com.EmployeeManagment.Source.Task_Scheduled.Entity;


import com.EmployeeManagment.Source.Content.Entity.Content;
import com.EmployeeManagment.Source.Employee.Entity.Employee;
import com.EmployeeManagment.Source.Task_Inserted.Entity.TaskInserted;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "TaskScheduled")
public class TaskScheduled implements Serializable {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id ;
    @ManyToOne
    @JoinColumn(name = "taskInserted_id", nullable = false)
    private TaskInserted taskInserted ;
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee ;

    private LocalDate  beginning ;

    private LocalDate end ;

    private boolean status ;
    @ManyToOne
    @JoinColumn(name = "content_id", nullable = false)
    private Content content ;

    //////////constructor for creation
     public   TaskScheduled(
             TaskInserted taskInserted  ,
             Employee employee,
             LocalDate  beginning,
             LocalDate end ,
             Content content

     ){
            this.setTaskInserted(taskInserted);
            this.setEmployee(employee);
            this.setBeginning(beginning);
            this.setEnd(end);
            this.setContent(content);

     }

    public TaskScheduled(Long id, TaskInserted t, Employee e, LocalDate beginning, LocalDate end, Content c) {
        this.setId(id);
        this.setTaskInserted(t);
        this.setEmployee(e);
        this.setBeginning(beginning);
        this.setEnd(end);
        this.setContent(c);
    }
}
