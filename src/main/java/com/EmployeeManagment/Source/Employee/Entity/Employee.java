package com.EmployeeManagment.Source.Employee.Entity;

import com.EmployeeManagment.Source.Absences.Entity.Absence;
import com.EmployeeManagment.Source.Pay_Stub.Entity.PayStub;
import com.EmployeeManagment.Source.Position.Entity.Position;
import com.EmployeeManagment.Source.Task_Scheduled.Entity.TaskScheduled;
import com.EmployeeManagment.Source.TimeOff.Entity.TimeOff;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
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
    private LocalDate birthday ;
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
            LocalDate birthday ,
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
            LocalDate birthday,
            String phone,
            Position p
    ) {
        this.setId(id);
        this.setName(name);
        this.setSurname(surname);
        this.setBirthday(birthday);
        this.setEmail(email);
        this.setPhone(phone);
        this.setPosition(p);
        this.setAddress(address);
        this.setPosition(p);

    }
}
