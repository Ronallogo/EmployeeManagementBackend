package com.EmployeeManagment.Source.Position.Entity;

import com.EmployeeManagment.Source.Task_Inserted.Entity.TaskInserted;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

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
    private String position_name ;
    private String position_description ;

    @JsonIgnore
    @OneToMany(mappedBy = "position", cascade = CascadeType.ALL)
    private List<TaskInserted> taskInsertedList ;
}
