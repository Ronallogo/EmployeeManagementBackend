package com.EmployeeManagment.Source.TimeOff.Entity;

import com.EmployeeManagment.Source.Employee.Entity.Employee;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "TimeOff")
public class TimeOff implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private Date beginning ;
    private Date end ;
    private String type ;
    private boolean status ;

    @ManyToOne
    @JoinColumn(name = "timeOffApply_id", nullable = false)
    private TimeOffApply timeOffApply ;


    ////// constructor personalized for TimeOff creation
    public TimeOff(
        Date beginning ,
        Date end ,
        String type ,
        boolean status
    ){
        this.setBeginning(beginning);

        this.setEnd(end);
        this.setType(type);
        this.setStatus(status);

    }

    public TimeOff(
            Date beginning,
            Date end,
            String type,
            boolean status,
            TimeOffApply t
    )
    {
       this.setBeginning(beginning);

       this.setType(type);
       this.setTimeOffApply(t);
       this.setStatus(status);
       this.setEnd(end);


    }
}
