package com.EmployeeManagment.Source.Task_Scheduled.Entity;


import com.EmployeeManagment.Source.Content.Entity.Content;
import com.EmployeeManagment.Source.Employee.Entity.Employee;
import com.EmployeeManagment.Source.Task_Inserted.Entity.TaskInserted;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date  beginning ;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date end ;

    private boolean status ;
    @ManyToOne
    @JoinColumn(name = "content_id", nullable = false)
    private Content content ;

    //////////constructor for creation
     public   TaskScheduled(
             TaskInserted taskInserted  ,
             Employee employee,
             Date  beginning,
             Date end ,
             Content content

     ){
            this.setTaskInserted(taskInserted);
            this.setEmployee(employee);
            this.setBeginning(beginning);
            this.setEnd(end);
            this.setContent(content);

     }

    public TaskScheduled(Long id, TaskInserted t, Employee e, Date beginning, Date end, Content c , boolean status) {
        this.setId(id);
        this.setTaskInserted(t);
        this.setEmployee(e);
        this.setBeginning(beginning);
        this.setEnd(end);
        this.setContent(c);
        this.setStatus(status);
    }
}
