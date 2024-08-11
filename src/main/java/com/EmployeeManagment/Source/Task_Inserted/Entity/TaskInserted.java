package com.EmployeeManagment.Source.Task_Inserted.Entity;


import com.EmployeeManagment.Source.Task.Entity.Task;
import com.EmployeeManagment.Source.Task_Scheduled.Entity.TaskScheduled;
import com.EmployeeManagment.Source.Position.Entity.Position;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "Task_Inserted")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class TaskInserted implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @ManyToOne
    @JoinColumn(name = "position_id", nullable = false)
    private Position position ;


    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task ;

    private Integer gain_task_post ;
    @JsonIgnore
    @OneToMany(mappedBy = "taskInserted", cascade = CascadeType.ALL)
    private List<TaskScheduled> taskScheduledList ;


    public TaskInserted(Position position, Task task, Integer gain_task_post){

       this.setPosition(position);
       this.setTask(task);
       this.setGain_task_post(gain_task_post);
    }

    public TaskInserted(Long setId, Position p, Task t, Integer gainTaskPost) {
        this.setPosition(p);
        this.setTask(t);
        this.setGain_task_post(gainTaskPost);
        this.setId(setId);
    }
}
