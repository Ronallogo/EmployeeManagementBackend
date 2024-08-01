package com.EmplyeeManagment.Source.Position.Entity;

import com.EmplyeeManagment.Source.Task_Inserted.Entity.TaskInserted;
import jakarta.persistence.*;
import lombok.*;
import org.apache.catalina.LifecycleState;

import java.io.Serializable;
import java.util.List;

/////this table position implementation
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Position")
public class Position implements Serializable {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String name ;
    private String description ;
    @OneToMany(mappedBy = "position", cascade = CascadeType.ALL)
    private List<TaskInserted> taskInsertedList ;
}
