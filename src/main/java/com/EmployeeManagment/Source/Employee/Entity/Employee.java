package com.EmployeeManagment.Source.Employee.Entity;

import com.EmployeeManagment.Source.Absences.Entity.Absence;
import com.EmployeeManagment.Source.Notification.Entity.Notification;
import com.EmployeeManagment.Source.Pay_Stub.Entity.PayStub;
import com.EmployeeManagment.Source.Position.Entity.Position;
import com.EmployeeManagment.Source.Repartition.Entities.Repartition;
import com.EmployeeManagment.Source.Security.entities.User;
import com.EmployeeManagment.Source.Task_Scheduled.Entity.TaskScheduled;
import com.EmployeeManagment.Source.TimeOff.Entity.TimeOffApply;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "Employee", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "surname", "email"})
})
public class Employee  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String name ;
    private String surname ;
    private String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date birthday ;
    private String phone ;
    private String address;
    @Lob
    @Column(name = "photo", columnDefinition = "MEDIUMBLOB")
    private byte[] photo;


    @ManyToOne
    @JoinColumn(name = "position_id", nullable = false)
    private Position position;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id" , unique = true)
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TimeOffApply> timeOffApplies;

    @JsonIgnore
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Absence> absences;

    @JsonIgnore
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PayStub> payStubs;

    @JsonIgnore
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Notification> notification;

    @JsonIgnore
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Repartition> repartition;
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
            Position p,
            byte[]photo
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
        this.setPhoto(photo);

    }



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
        this.setPosition(p);
        this.setAddress(address);
        this.setPosition(p);


    }



    public Employee(

            String name,
            String surname,
            String email,
            String address,
            Date birthday,
            String phone,
            Position p ,
            User user
    ) {

        this.setName(name);
        this.setSurname(surname);
        this.setBirthday(birthday);
        this.setEmail(email);
        this.setPhone(phone);
        this.setPosition(p);
        this.setAddress(address);
        this.setPosition(p);
        this.setUser(user);

    }

    public Employee(
            Long id,
            String name,
            String surname,
            String email,
            String address,
            Date birthday,
            String phone,
            Position p,
            User user
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
        this.setUser(user);
    }

    public Employee(String name, String surname, String email, String address, Date birthday, String phone, Position p, User user, byte[] photo) {



        this.setId(id);
        this.setName(name);
        this.setSurname(surname);
        this.setBirthday(birthday);
        this.setEmail(email);
        this.setPhone(phone);
        this.setPosition(p);
        this.setAddress(address);
        this.setPosition(p);
        this.setUser(user);
        this.setPhoto(photo);
    }

    public Employee(Long id, String name, String surname, String email, String address, Date birthday, String phone, Position p, User user, byte[] photo) {

        this.setId(id);
        this.setName(name);
        this.setSurname(surname);
        this.setBirthday(birthday);
        this.setEmail(email);
        this.setPhone(phone);
        this.setPosition(p);
        this.setAddress(address);
        this.setPosition(p);
        this.setUser(user);
        this.setPhoto(photo);
    }

}
