package com.EmplyeeManagment.Source.Task_Inserted.Entity;


import com.EmplyeeManagment.Source.Position.Entity.Position;
import com.EmplyeeManagment.Source.Task.Entity.Task;
import com.EmplyeeManagment.Source.Task_Scheduled.Entity.TaskScheduled;
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

    private Integer gain_by_task ;

    @OneToMany(mappedBy = "taskInserted", cascade = CascadeType.ALL)
    private List<TaskScheduled> taskScheduledList ;
}
