package com.EmployeeManagment.Source.Absences.Entity;


import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AbsenceRequest {

    private Long id ;
    private Date absence_day ;
    private String reason ;

    private Long employee ;



    /////personalize constructor for creation
    public AbsenceRequest(
           Date day ,
           String reason,
           Long employee
    )
    {
        this.setAbsence_day(day);
        this.setReason(reason);
        this.setEmployee(employee);
    }
}
