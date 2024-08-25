package com.EmployeeManagment.Source.Task_Inserted.Entity;


import com.EmployeeManagment.Source.Task.Entity.Task;
import com.EmployeeManagment.Source.Task_Scheduled.Entity.TaskScheduled;
import com.EmployeeManagment.Source.Position.Entity.Position;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date date_insertion ;

    public TaskInserted(Position position, Task task, Date date ,  Integer gain_task_post){

       this.setPosition(position);
       this.setTask(task);
       this.setGain_task_post(gain_task_post);
        this.setDate_insertion(date);

    }

    public TaskInserted(Long setId, Position p, Task t,Date date  , Integer gainTaskPost) {
        this.setPosition(p);
        this.setTask(t);
        this.setGain_task_post(gainTaskPost);
        this.setId(setId);
        this.setDate_insertion(date);
    }
}
