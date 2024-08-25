package com.EmployeeManagment.Source.Task.Entity;


import com.EmployeeManagment.Source.Task_Inserted.Entity.TaskInserted;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;


///// implementation of table  Task
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Task", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"task_name","task_description"})
})
public class Task implements Serializable {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String task_name ;
    private String task_description ;

    @JsonIgnore
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<TaskInserted> taskInsertedList ;

}
