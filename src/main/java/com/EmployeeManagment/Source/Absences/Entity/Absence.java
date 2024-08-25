package com.EmployeeManagment.Source.Absences.Entity;

import com.EmployeeManagment.Source.Employee.Entity.Employee;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Absences", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"absence"})
})
public class Absence implements Serializable {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date absence_day ;
    private String reason ;
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee ;


    /////personalize constructor for creation
    public Absence(
            Date day ,
            String reason,
            Employee employee
    )
    {
        this.setAbsence_day(day);
        this.setReason(reason);
        this.setEmployee(employee);
    }

}
