package com.EmployeeManagment.Source.Task_Scheduled.Entity;


import com.EmployeeManagment.Source.Content.Entity.Content;
import com.EmployeeManagment.Source.Employee.Entity.Employee;
import com.EmployeeManagment.Source.Task_Inserted.Entity.TaskInserted;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskScheduledRequest {


    private Long id ;

    private Long taskInserted ;

    private Long employee ;
    private LocalDate beginning ;
    private LocalDate end ;
    private boolean status ;

    private Long content ;



    public TaskScheduledRequest(
         Long taskInserted ,
         Long employee ,
         LocalDate beginning ,
         LocalDate end ,
         boolean status ,
         Long content
    ){
        this.setTaskInserted(taskInserted);
        this.setEmployee(employee);
        this.setBeginning(beginning);
        this.setEnd(end);
        this.setStatus(status);
        this.setContent(content);


    }
}
