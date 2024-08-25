package com.EmployeeManagment.Source.TimeOff.Entity;

import com.EmployeeManagment.Source.Employee.Entity.Employee;
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
@Table(name = "TimeOffApply")
public class TimeOffApply  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date beginning ;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date end ;
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee ;
    private String type ;
    private String Apply ;
    private boolean validate ;
    @JsonIgnore
    @OneToMany(mappedBy = "timeOffApply", cascade = CascadeType.ALL)
    private List<TimeOff> timeOffs ;


    public TimeOffApply(Employee employee, String apply , String type , Date beginning , Date end , boolean validate) {
        this.setApply(apply);
        this.setEmployee(employee);
        this.setValidate(validate);
        this.setBeginning(beginning);
        this.setEnd(end);
        this.setType(type);
    }

    public TimeOffApply(Long id, Date end, Date beginning, Employee e, String type, String apply, boolean validate) {

        this.setApply(apply);
        this.setEmployee(e);
        this.setValidate(validate);
        this.setBeginning(beginning);
        this.setEnd(end);
        this.setType(type);
    }
}
