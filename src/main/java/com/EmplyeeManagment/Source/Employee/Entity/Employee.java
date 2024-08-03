package com.EmplyeeManagment.Source.Employee.Entity;

import com.EmplyeeManagment.Source.Absences.Entity.Absence;
import com.EmplyeeManagment.Source.Pay_Stub.Entity.PayStub;
import com.EmplyeeManagment.Source.Position.Entity.Position;
import com.EmplyeeManagment.Source.Task.Entity.Task;
import com.EmplyeeManagment.Source.Task_Scheduled.Entity.TaskScheduled;
import com.EmplyeeManagment.Source.TimeOff.Entity.TimeOff;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private String address;


    @ManyToOne
    @JoinColumn(name = "position_id", nullable = false)
    private Position position;

    @JsonIgnore
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<TaskScheduled> taskScheduledList ;

    @JsonIgnore
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<TimeOff> timeOffs ;

    @JsonIgnore
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Absence> absences ;

    @JsonIgnore
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<PayStub> payStubs ;

    /////// constructor personalize for creation
    public Employee(
            String name ,
            String surname ,
            String email ,
            String address ,
            Date birthday ,
            String phone ,
            Position position
    ){
        this.setName(name);
        this.setSurname(surname);
        this.setBirthday(birthday);
        this.setEmail(email);
        this.setPhone(phone);
        this.setPosition(position);
        this.setAddress(address);

    }

    //////// constructor  personalize for updating
    public Employee(
            Long id,
            String name,
            String surname,
            String email,
            String address,
            Date birthday,
            String phone,
            Position p
    ) {
        this.setId(id);
        this.setName(name);
        this.setSurname(surname);
        this.setBirthday(birthday);
        this.setEmail(email);
        this.setPhone(phone);
        this.setPosition(position);
        this.setAddress(address);
        this.setPosition(p);

    }
}
