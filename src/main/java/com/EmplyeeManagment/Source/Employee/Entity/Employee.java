package com.EmplyeeManagment.Source.Employee.Entity;

import com.EmplyeeManagment.Source.Absences.Entity.Absence;
import com.EmplyeeManagment.Source.Pay_Stub.Entity.PayStub;
import com.EmplyeeManagment.Source.Position.Entity.Position;
import com.EmplyeeManagment.Source.Task.Entity.Task;
import com.EmplyeeManagment.Source.Task_Scheduled.Entity.TaskScheduled;
import com.EmplyeeManagment.Source.TimeOff.Entity.TimeOff;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Employee")
public class Employee  implements Serializable {
    @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String name ;
    private String surname ;
    private String email;
    private Date birthday ;
    private String phone ;
    @ManyToOne
    @JoinColumn(name = "position_id", nullable = false)
    private Position position;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<TaskScheduled> taskScheduledList ;
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<TimeOff> timeOffs ;
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Absence> absences ;
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<PayStub> payStubs ;



}
