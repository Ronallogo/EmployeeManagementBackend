package com.EmplyeeManagment.Source.Task_Scheduled.Entity;


import com.EmplyeeManagment.Source.Content.Entity.Content;
import com.EmplyeeManagment.Source.Employee.Entity.Employee;
import com.EmplyeeManagment.Source.Task_Inserted.Entity.TaskInserted;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "TaskScheduled")
public class TaskScheduled implements Serializable {
    @Id@GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id ;
    @ManyToOne
    @JoinColumn(name = "taskInserted_id", nullable = false)
    private TaskInserted taskInserted ;
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee ;
    private Date  beginning ;
    private Date end ;
    private boolean status ;
    @ManyToOne
    @JoinColumn(name = "content_id", nullable = false)
    private Content content ;
}
