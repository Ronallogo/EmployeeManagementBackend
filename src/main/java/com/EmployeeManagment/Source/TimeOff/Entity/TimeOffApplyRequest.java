package com.EmployeeManagment.Source.TimeOff.Entity;


import com.EmployeeManagment.Source.Employee.Entity.Employee;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TimeOffApplyRequest {

    private Long id ;
    private Long employee ;
    private String Apply ;

    private boolean validate ;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date beginning ;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date end ;
    private String type ;






    TimeOffApplyRequest( Long employee , String type , Date beginning , Date end , String apply , boolean validate){
        this.setApply(apply);
        this.setEmployee(employee);
        this.setValidate(validate);
        this.setBeginning(beginning);
        this.setEnd(end);
        this.setType(type);
    }
}
