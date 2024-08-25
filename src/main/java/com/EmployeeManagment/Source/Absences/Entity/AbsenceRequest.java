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

    private String email;



    /////personalize constructor for creation
    public AbsenceRequest(
           Date day ,
           String reason,
           String employee
    )
    {
        this.setAbsence_day(day);
        this.setReason(reason);
        this.setEmail(employee);
    }
}
