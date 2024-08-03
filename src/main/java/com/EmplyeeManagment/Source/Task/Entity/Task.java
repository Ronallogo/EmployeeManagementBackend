package com.EmplyeeManagment.Source.Task.Entity;


import com.EmplyeeManagment.Source.Task_Inserted.Entity.TaskInserted;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.apache.catalina.LifecycleState;

import java.io.Serializable;
import java.util.List;


///// implementation of table  Task
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Task")
public class Task implements Serializable {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String task_name ;
    private String task_description ;

    @JsonIgnore
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<TaskInserted> taskInsertedList ;

}
